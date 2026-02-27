package nl.joozd.parsing

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.Bestand
import nl.joozd.mdto.objects.ChecksumGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import nl.joozd.utils.requireNextTagAsStartElement
import java.net.URI
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement

private const val OMVANG_TAG = "omvang"
private const val BESTANDSFORMAAT_TAG = "bestandsformaat"
private const val CHECKSUM_TAG = "checksum"
private const val URL_BESTAND_TAG = "URLBestand"
private const val IS_REPRESENTATIE_VAN_TAG = "isRepresentatieVan"

/**
 * Parses a `bestandType` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by [startEvent].
 * This function consumes all events until the matching `END_ELEMENT` is encountered.
 *
 * Content model:
 * - base `objectType` (required): `identificatie` (1..∞), `naam` (1)
 * - extension sequence (required unless stated otherwise):
 *   - `omvang` (integer)
 *   - `bestandsformaat` (begripGegevens)
 *   - `checksum` (1..∞)
 *   - `URLBestand` (anyURI, optional)
 *   - `isRepresentatieVan` (verwijzingGegevens)
 */
internal fun getBestand(
    reader: XMLEventReader,
    startEvent: StartElement,
): Bestand {

    val startEventName = startEvent.name

    // --- base ---
    val base = readObjectTypeBase(reader, startEventName)

    // --- extension ---
    var omvang: Long? = null
    var bestandsformaat: BegripGegevens? = null
    val checksum = mutableListOf<ChecksumGegevens>()
    var URLBestand: URI? = null
    var isRepresentatieVan: VerwijzingGegevens? = null

    var currentEvent = reader.requireNextTagAsStartElement()

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        when (currentEvent.name.localPart) {

            OMVANG_TAG -> {
                val text = reader.readSimpleElementText(currentEvent)
                omvang = text.toLong()
            }

            BESTANDSFORMAAT_TAG ->
                bestandsformaat = getBegripGegevens(reader, currentEvent)

            CHECKSUM_TAG ->
                checksum += getChecksumGegevens(reader, currentEvent)

            URL_BESTAND_TAG -> {
                val text = reader.readSimpleElementText(currentEvent)
                URLBestand = URI.create(text)
            }

            IS_REPRESENTATIE_VAN_TAG ->
                isRepresentatieVan = getVerwijzingGegevens(reader, currentEvent)
        }

        currentEvent = reader.requireNextTagAsStartElement()
    }

    if (omvang == null ||
        bestandsformaat == null ||
        checksum.isEmpty() ||
        isRepresentatieVan == null
    ) {
        throw IllegalStateException(
            "Bestand incompleet bij regel ${startEvent.location}"
        )
    }

    return Bestand(
        identificatie = base.identificaties,
        naam = base.naam,
        omvang = omvang,
        bestandsformaat = bestandsformaat,
        checksum = checksum,
        urlBestand = URLBestand,
        isRepresentatieVan = isRepresentatieVan,
    )
}