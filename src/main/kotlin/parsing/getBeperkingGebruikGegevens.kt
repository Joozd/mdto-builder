package nl.joozd.parsing

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.BeperkingGebruikGegevens
import nl.joozd.mdto.objects.TermijnGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import nl.joozd.utils.requireNextTagAsStartElement
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement

private const val BEPERKING_GEBRUIK_TYPE_TAG = "beperkingGebruikType"
private const val BEPERKING_GEBRUIK_NADERE_BESCHRIJVING_TAG = "beperkingGebruikNadereBeschrijving"
private const val BEPERKING_GEBRUIK_DOCUMENTATIE_TAG = "beperkingGebruikDocumentatie"
private const val BEPERKING_GEBRUIK_TERMIJN_TAG = "beperkingGebruikTermijn"

/**
 * Parses a `beperkingGebruikGegevens` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by [startEvent].
 * This function consumes all events until the matching `END_ELEMENT` is encountered.
 *
 * Required:
 * - `beperkingGebruikType` (begripGegevens)
 *
 * Optional:
 * - `beperkingGebruikNadereBeschrijving` (string)
 * - `beperkingGebruikDocumentatie` (verwijzingGegevens, unbounded)
 * - `beperkingGebruikTermijn` (termijnGegevens)
 */
internal fun getBeperkingGebruikGegevens(
    reader: XMLEventReader,
    startEvent: StartElement,
): BeperkingGebruikGegevens {

    val startEventName = startEvent.name
    var currentEvent: StartElement = startEvent

    var beperkingGebruikType: BegripGegevens? = null
    var beperkingGebruikNadereBeschrijving: String? = null
    val beperkingGebruikDocumentatie = mutableListOf<VerwijzingGegevens>()
    var beperkingGebruikTermijn: TermijnGegevens? = null

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        currentEvent = reader.requireNextTagAsStartElement()

        when (currentEvent.name.localPart) {

            BEPERKING_GEBRUIK_TYPE_TAG ->
                beperkingGebruikType =
                    getBegripGegevens(reader, currentEvent)

            BEPERKING_GEBRUIK_NADERE_BESCHRIJVING_TAG ->
                beperkingGebruikNadereBeschrijving =
                    reader.readSimpleElementText(currentEvent)

            BEPERKING_GEBRUIK_DOCUMENTATIE_TAG ->
                beperkingGebruikDocumentatie +=
                    getVerwijzingGegevens(reader, currentEvent)

            BEPERKING_GEBRUIK_TERMIJN_TAG ->
                beperkingGebruikTermijn =
                    getTermijnGegevens(reader, currentEvent)
        }
    }

    if (beperkingGebruikType == null) {
        throw IllegalStateException(
            "BeperkingGebruikGegevens incompleet bij regel ${startEvent.location}"
        )
    }

    return BeperkingGebruikGegevens(
        beperkingGebruikType = beperkingGebruikType,
        beperkingGebruikNadereBeschrijving = beperkingGebruikNadereBeschrijving,
        beperkingGebruikDocumentatie = beperkingGebruikDocumentatie,
        beperkingGebruikTermijn = beperkingGebruikTermijn,
    )
}