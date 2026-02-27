package nl.joozd.parsing

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.TermijnGegevens
import nl.joozd.mdto.types.XsdDateUnion
import nl.joozd.mdto.types.XsdDuration
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import java.time.LocalDate
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val TERMIJN_TRIGGER_START_LOOPTIJD_TAG = "termijnTriggerStartLooptijd"
private const val TERMIJN_STARTDATUM_LOOPTIJD_TAG = "termijnStartdatumLooptijd"
private const val TERMIJN_LOOPTIJD_TAG = "termijnLooptijd"
private const val TERMIJN_EINDDATUM_TAG = "termijnEinddatum"

/**
 * Parses a `termijnGegevens` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by [startEvent].
 * This function consumes all events until the matching `END_ELEMENT` is encountered.
 *
 * All child elements are optional:
 * - `termijnTriggerStartLooptijd` (begripGegevens)
 * - `termijnStartdatumLooptijd` (xsd:date)
 * - `termijnLooptijd` (xsd:duration)
 * - `termijnEinddatum` (union: gYear | gYearMonth | date)
 */
internal fun getTermijnGegevens(
    reader: XMLEventReader,
    startEvent: StartElement,
): TermijnGegevens {

    val startEventName = startEvent.name
    var currentEvent: XMLEvent = startEvent

    var termijnTriggerStartLooptijd: BegripGegevens? = null
    var termijnStartdatumLooptijd: LocalDate? = null
    var termijnLooptijd: XsdDuration? = null
    var termijnEinddatum: XsdDateUnion? = null

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        val nextEvent = reader.nextTag()
        if(nextEvent.isEndElement) {
            currentEvent = nextEvent
            continue
        }
        currentEvent = nextEvent.asStartElement()

        when (currentEvent.name.localPart) {

            TERMIJN_TRIGGER_START_LOOPTIJD_TAG ->
                termijnTriggerStartLooptijd = getBegripGegevens(reader, currentEvent)

            TERMIJN_STARTDATUM_LOOPTIJD_TAG -> {
                val text = reader.readSimpleElementText(currentEvent)
                termijnStartdatumLooptijd = LocalDate.parse(text)
            }

            TERMIJN_LOOPTIJD_TAG -> {
                val text = reader.readSimpleElementText(currentEvent)
                termijnLooptijd = XsdDuration.parse(text)
            }

            TERMIJN_EINDDATUM_TAG -> {
                val text = reader.readSimpleElementText(currentEvent)
                termijnEinddatum = XsdDateUnion.fromString(text)
            }
        }
    }

    return TermijnGegevens(
        termijnTriggerStartLooptijd = termijnTriggerStartLooptijd,
        termijnStartdatumLooptijd = termijnStartdatumLooptijd,
        termijnLooptijd = termijnLooptijd,
        termijnEinddatum = termijnEinddatum,
    )
}