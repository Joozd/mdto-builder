package nl.joozd.parsing

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.GerelateerdInformatieobjectGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.utils.isEndEventFor
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val GERELATEERD_INFORMATIEOBJECT_VERWIJZING_TAG =
    "gerelateerdInformatieobjectVerwijzing"
private const val GERELATEERD_INFORMATIEOBJECT_TYPE_RELATIE_TAG =
    "gerelateerdInformatieobjectTypeRelatie"

/**
 * Parses a `gerelateerdInformatieobjectGegevens` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by [startEvent].
 * This function consumes all events until the matching `END_ELEMENT` is encountered.
 *
 * Required (in order):
 * - `gerelateerdInformatieobjectVerwijzing` (verwijzingGegevens)
 * - `gerelateerdInformatieobjectTypeRelatie` (begripGegevens)
 */
internal fun getGerelateerdInformatieobjectGegevens(
    reader: XMLEventReader,
    startEvent: StartElement,
): GerelateerdInformatieobjectGegevens {

    val startEventName = startEvent.name
    var currentEvent: XMLEvent = startEvent

    var gerelateerdInformatieobjectVerwijzing: VerwijzingGegevens? = null
    var gerelateerdInformatieobjectTypeRelatie: BegripGegevens? = null

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        val nextEvent = reader.nextTag()
        if(nextEvent.isEndElement) {
            currentEvent = nextEvent
            continue
        }
        currentEvent = nextEvent.asStartElement()

        when (currentEvent.name.localPart) {

            GERELATEERD_INFORMATIEOBJECT_VERWIJZING_TAG ->
                gerelateerdInformatieobjectVerwijzing =
                    getVerwijzingGegevens(reader, currentEvent)

            GERELATEERD_INFORMATIEOBJECT_TYPE_RELATIE_TAG ->
                gerelateerdInformatieobjectTypeRelatie =
                    getBegripGegevens(reader, currentEvent)
        }
    }

    if (gerelateerdInformatieobjectVerwijzing == null ||
        gerelateerdInformatieobjectTypeRelatie == null
    ) {
        throw IllegalStateException(
            "GerelateerdInformatieobjectGegevens incompleet bij regel ${startEvent.location}"
        )
    }

    return GerelateerdInformatieobjectGegevens(
        gerelateerdInformatieobjectVerwijzing = gerelateerdInformatieobjectVerwijzing,
        gerelateerdInformatieobjectTypeRelatie = gerelateerdInformatieobjectTypeRelatie,
    )
}