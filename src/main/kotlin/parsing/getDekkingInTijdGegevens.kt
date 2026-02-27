package nl.joozd.parsing

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.DekkingInTijdGegevens
import nl.joozd.mdto.types.XsdDateUnion
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val DEKKING_IN_TIJD_TYPE_TAG = "dekkingInTijdType"
private const val DEKKING_IN_TIJD_BEGINDATUM_TAG = "dekkingInTijdBegindatum"
private const val DEKKING_IN_TIJD_EINDDATUM_TAG = "dekkingInTijdEinddatum"

/**
 * Parses a `dekkingInTijdGegevens` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by [startEvent].
 * This function consumes all events until the matching `END_ELEMENT` is encountered.
 *
 * Required (in order):
 * - `dekkingInTijdType` (begripGegevens)
 * - `dekkingInTijdBegindatum` (union: gYear | gYearMonth | date)
 *
 * Optional:
 * - `dekkingInTijdEinddatum` (union: gYear | gYearMonth | date)
 */
internal fun getDekkingInTijdGegevens(
    reader: XMLEventReader,
    startEvent: StartElement,
): DekkingInTijdGegevens {

    val startEventName = startEvent.name
    var currentEvent: XMLEvent = startEvent

    var dekkingInTijdType: BegripGegevens? = null
    var dekkingInTijdBegindatum: XsdDateUnion? = null
    var dekkingInTijdEinddatum: XsdDateUnion? = null

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        val nextEvent = reader.nextTag()
        if(nextEvent.isEndElement) {
            currentEvent = nextEvent
            continue
        }
        currentEvent = nextEvent.asStartElement()

        when (currentEvent.name.localPart) {

            DEKKING_IN_TIJD_TYPE_TAG ->
                dekkingInTijdType = getBegripGegevens(reader, currentEvent)

            DEKKING_IN_TIJD_BEGINDATUM_TAG -> {
                val text = reader.readSimpleElementText(currentEvent)
                dekkingInTijdBegindatum = XsdDateUnion.fromString(text)
            }

            DEKKING_IN_TIJD_EINDDATUM_TAG -> {
                val text = reader.readSimpleElementText(currentEvent)
                dekkingInTijdEinddatum = XsdDateUnion.fromString(text)
            }
        }
    }

    if (dekkingInTijdType == null || dekkingInTijdBegindatum == null) {
        throw IllegalStateException(
            "DekkingInTijdGegevens incompleet bij regel ${startEvent.location}"
        )
    }

    return DekkingInTijdGegevens(
        dekkingInTijdType = dekkingInTijdType,
        dekkingInTijdBegindatum = dekkingInTijdBegindatum,
        dekkingInTijdEinddatum = dekkingInTijdEinddatum,
    )
}