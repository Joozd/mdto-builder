package nl.joozd.parsing

import nl.joozd.mdto.objects.RaadpleeglocatieGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import java.net.URI
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val RAADPLEEGLOCATIE_FYSIEK_TAG = "raadpleeglocatieFysiek"
private const val RAADPLEEGLOCATIE_ONLINE_TAG = "raadpleeglocatieOnline"

/**
 * Parses a `raadpleeglocatieGegevens` element.
 *
 * Expects the reader to be positioned on the START_ELEMENT of
 * `raadpleeglocatieGegevens` and consumes events until the matching END_ELEMENT.
 */
fun getRaadpleeglocatieGegevens(
    reader: XMLEventReader,
    startEvent: StartElement,
): RaadpleeglocatieGegevens {

    val startEventName = startEvent.name
    var currentEvent: XMLEvent = startEvent

    val raadpleeglocatieFysiek = mutableListOf<VerwijzingGegevens>()
    val raadpleeglocatieOnline = mutableListOf<URI>()

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        val nextEvent = reader.nextTag()
        if(nextEvent.isEndElement) {
            currentEvent = nextEvent
            continue
        }
        currentEvent = nextEvent.asStartElement()

        when (currentEvent.name.localPart) {

            RAADPLEEGLOCATIE_FYSIEK_TAG ->
                raadpleeglocatieFysiek += getVerwijzingGegevens(reader, currentEvent)

            RAADPLEEGLOCATIE_ONLINE_TAG ->
                raadpleeglocatieOnline +=
                    URI.create(reader.readSimpleElementText(currentEvent))
        }
    }

    return RaadpleeglocatieGegevens(
        raadpleeglocatieFysiek = raadpleeglocatieFysiek,
        raadpleeglocatieOnline = raadpleeglocatieOnline,
    )
}