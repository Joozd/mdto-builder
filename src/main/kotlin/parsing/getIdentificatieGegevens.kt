package nl.joozd.parsing

import nl.joozd.mdto.objects.IdentificatieGegevens
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val IDENTIFICATIE_KENMERK_TAG = "identificatieKenmerk"
private const val IDENTIFICATIE_BRON_TAG = "identificatieBron"

/**
 * Parses an `identificatieGegevens` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by
 * [startEvent], which is expected to correspond to an element of type
 * `identificatieGegevens` as defined in the MDTO XSD.
 *
 * This function consumes all events until the matching `END_ELEMENT`
 * for [startEvent] is encountered.
 *
 * According to the schema, the expected child elements (in order) are:
 * - `identificatieKenmerk` (required, simple text)
 * - `identificatieBron` (required, simple text)
 *
 * @param reader The [XMLEventReader] to read from.
 * @param startEvent The `START_ELEMENT` for the `identificatieGegevens` block.
 *
 * @return A fully populated [IdentificatieGegevens] instance.
 *
 * @throws XMLStreamException If an error occurs while reading from the XML stream.
 * @throws IllegalStateException If one or more required elements are missing.
 *
 * After successful return, the reader will be positioned on the matching
 * `END_ELEMENT` of [startEvent].
 */
internal fun getIdentificatieGegevens(
    reader: XMLEventReader,
    startEvent: StartElement
): IdentificatieGegevens {

    val startEventName = startEvent.name
    var currentEvent: XMLEvent = startEvent

    var kenmerk: String? = null
    var bron: String? = null

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        val nextEvent = reader.nextTag()
        if(nextEvent.isEndElement) {
            currentEvent = nextEvent
            continue
        }
        currentEvent = nextEvent.asStartElement()

        when (currentEvent.name.localPart) {
            IDENTIFICATIE_KENMERK_TAG ->
                kenmerk = reader.readSimpleElementText(currentEvent)

            IDENTIFICATIE_BRON_TAG ->
                bron = reader.readSimpleElementText(currentEvent)
        }
    }

    if (kenmerk == null || bron == null) {
        throw IllegalStateException(
            "ERROR 0004: IdentificatieGegevens incompleet bij regel ${startEvent.location}"
        )
    }

    return IdentificatieGegevens(
        identificatieKenmerk = kenmerk,
        identificatieBron = bron
    )
}