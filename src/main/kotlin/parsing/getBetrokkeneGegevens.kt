package nl.joozd.parsing

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.BetrokkeneGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.requireNextTagAsStartElement
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement

private const val BETROKKENE_TYPE_RELATIE_TAG = "betrokkeneTypeRelatie"
private const val BETROKKENE_ACTOR_TAG = "betrokkeneActor"

/**
 * Parses a `betrokkeneGegevens` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by [startEvent].
 * This function consumes all events until the matching `END_ELEMENT` is encountered.
 *
 * Required (in order):
 * - `betrokkeneTypeRelatie` (begripGegevens)
 * - `betrokkeneActor` (verwijzingGegevens)
 */
internal fun getBetrokkeneGegevens(
    reader: XMLEventReader,
    startEvent: StartElement,
): BetrokkeneGegevens {

    val startEventName = startEvent.name
    var currentEvent: StartElement = startEvent

    var betrokkeneTypeRelatie: BegripGegevens? = null
    var betrokkeneActor: VerwijzingGegevens? = null

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        currentEvent = reader.requireNextTagAsStartElement()

        when (currentEvent.name.localPart) {

            BETROKKENE_TYPE_RELATIE_TAG ->
                betrokkeneTypeRelatie =
                    getBegripGegevens(reader, currentEvent)

            BETROKKENE_ACTOR_TAG ->
                betrokkeneActor =
                    getVerwijzingGegevens(reader, currentEvent)
        }
    }

    if (betrokkeneTypeRelatie == null || betrokkeneActor == null) {
        throw IllegalStateException(
            "BetrokkeneGegevens incompleet bij regel ${startEvent.location}"
        )
    }

    return BetrokkeneGegevens(
        betrokkeneTypeRelatie = betrokkeneTypeRelatie,
        betrokkeneActor = betrokkeneActor,
    )
}