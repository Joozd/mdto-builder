package nl.joozd.parsing

import nl.joozd.mdto.objects.*
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import java.net.URI
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val IDENTIFICATIE_TAG = "identificatie"
private const val NAAM_TAG = "naam"
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
    val identificaties = mutableListOf<IdentificatieGegevens>()
    var naam: String? = null

    // --- extension ---

    var omvang: Long? = null
    var bestandsformaat: BegripGegevens? = null
    val checksum = mutableListOf<ChecksumGegevens>()
    var URLBestand: URI? = null
    var isRepresentatieVan: VerwijzingGegevens? = null

    var currentEvent: XMLEvent = startEvent

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        val nextEvent = reader.nextTag()
        if(nextEvent.isEndElement) {
            currentEvent = nextEvent
            continue
        }
        currentEvent = nextEvent.asStartElement()
        when (currentEvent.name.localPart) {
            IDENTIFICATIE_TAG ->
                identificaties += getIdentificatieGegevens(reader, currentEvent)

            NAAM_TAG -> naam = reader.readSimpleElementText(currentEvent)

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
    }

    if (naam == null ||
        omvang == null ||
        bestandsformaat == null ||
        checksum.isEmpty() ||
        isRepresentatieVan == null
    ) {
        throw IllegalStateException(
            "Bestand incompleet bij regel ${startEvent.location}"
        )
    }

    return Bestand(
        identificatie = identificaties,
        naam = naam,
        omvang = omvang,
        bestandsformaat = bestandsformaat,
        checksum = checksum,
        urlBestand = URLBestand,
        isRepresentatieVan = isRepresentatieVan,
    )
}