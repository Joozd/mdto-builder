package nl.joozd.parsing

import nl.joozd.mdto.objects.IdentificatieGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val VERWIJZING_NAAM_TAG = "verwijzingNaam"
private const val VERWIJZING_IDENTIFICATIE_TAG = "verwijzingIdentificatie"

/**
 * Parses a `verwijzingGegevens` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by
 * [startEvent], which is expected to correspond to an element of type
 * `verwijzingGegevens` as defined in the MDTO XSD.
 *
 * This function consumes all events until the matching `END_ELEMENT`
 * for [startEvent] is encountered.
 *
 * According to the schema, the expected child elements (in order) are:
 * - `verwijzingNaam` (required, simple text)
 * - `verwijzingIdentificatie` (optional, complex type `identificatieGegevens`)
 *
 * @param reader The [XMLEventReader] to read from.
 * @param startEvent The `START_ELEMENT` for the `verwijzingGegevens` block.
 *
 * @return A fully populated [VerwijzingGegevens] instance.
 *
 * @throws XMLStreamException If an error occurs while reading from the XML stream.
 * @throws IllegalStateException If the required element (`verwijzingNaam`) is missing.
 *
 * After successful return, the reader will be positioned on the matching
 * `END_ELEMENT` of [startEvent].
 */
internal fun getVerwijzingGegevens(
    reader: XMLEventReader,
    startEvent: StartElement
): VerwijzingGegevens {

    val startEventName = startEvent.name
    var currentEvent: XMLEvent = startEvent

    var naam: String? = null
    var identificatie: IdentificatieGegevens? = null // optional

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        val nextEvent = reader.nextTag()
        if(nextEvent.isEndElement) {
            currentEvent = nextEvent
            continue
        }
        currentEvent = nextEvent.asStartElement()

        when (currentEvent.name.localPart) {
            VERWIJZING_NAAM_TAG ->
                naam = reader.readSimpleElementText(currentEvent)

            VERWIJZING_IDENTIFICATIE_TAG ->
                identificatie = getIdentificatieGegevens(reader, currentEvent)
        }
    }

    if (naam == null) {
        throw IllegalStateException(
            "ERROR 0003: VerwijzingGegevens incompleet bij regel ${startEvent.location}"
        )
    }

    return VerwijzingGegevens(
        verwijzingNaam = naam,
        verwijzingIdentificatie = identificatie
    )
}