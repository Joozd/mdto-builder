package nl.joozd.parsing

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.EventGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.mdto.types.XsdDateOrDateTimeUnion
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val EVENT_TYPE_TAG = "eventType"
private const val EVENT_TIJD_TAG = "eventTijd"
private const val EVENT_VERANTWOORDELIJKE_ACTOR_TAG = "eventVerantwoordelijkeActor"
private const val EVENT_RESULTAAT_TAG = "eventResultaat"

/**
 * Parses an `eventGegevens` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by [startEvent].
 * This function consumes all events until the matching `END_ELEMENT` is encountered.
 *
 * Required:
 * - `eventType` (begripGegevens)
 *
 * Optional:
 * - `eventTijd` (union: gYear | gYearMonth | date | dateTime)
 * - `eventVerantwoordelijkeActor` (verwijzingGegevens)
 * - `eventResultaat` (string)
 */
internal fun getEventGegevens(
    reader: XMLEventReader,
    startEvent: StartElement,
): EventGegevens {

    val startEventName = startEvent.name
    var currentEvent: XMLEvent = startEvent

    var eventType: BegripGegevens? = null
    var eventTijd: XsdDateOrDateTimeUnion? = null
    var eventVerantwoordelijkeActor: VerwijzingGegevens? = null
    var eventResultaat: String? = null

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        val nextEvent = reader.nextTag()
        if(nextEvent.isEndElement) {
            currentEvent = nextEvent
            continue
        }
        currentEvent = nextEvent.asStartElement()

        when (currentEvent.name.localPart) {

            EVENT_TYPE_TAG ->
                eventType = getBegripGegevens(reader, currentEvent)

            EVENT_TIJD_TAG -> {
                val text = reader.readSimpleElementText(currentEvent)
                eventTijd = XsdDateOrDateTimeUnion.fromString(text)
            }

            EVENT_VERANTWOORDELIJKE_ACTOR_TAG ->
                eventVerantwoordelijkeActor = getVerwijzingGegevens(reader, currentEvent)

            EVENT_RESULTAAT_TAG ->
                eventResultaat = reader.readSimpleElementText(currentEvent)
        }
    }

    if (eventType == null) {
        throw IllegalStateException(
            "EventGegevens incompleet bij regel ${startEvent.location}"
        )
    }

    return EventGegevens(
        eventType = eventType,
        eventTijd = eventTijd,
        eventVerantwoordelijkeActor = eventVerantwoordelijkeActor,
        eventResultaat = eventResultaat,
    )
}