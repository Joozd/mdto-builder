package nl.joozd.parsing

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import nl.joozd.utils.requireNextTagAsStartElement
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement

private const val BEGRIP_LABEL_TAG = "begripLabel"
private const val BEGRIP_CODE_TAG = "begripCode"
private const val BEGRIP_BEGRIPPENLIJST_TAG = "begripBegrippenlijst"

/**
 * Parses a `begripGegevens` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by
 * [startEvent], which is expected to correspond to an element of type
 * `begripGegevens` as defined in the MDTO XSD.
 *
 * This function consumes all events until the matching `END_ELEMENT`
 * for [startEvent] is encountered.
 *
 * According to the schema, the expected child elements (in order) are:
 * - `begripLabel` (required, simple text)
 * - `begripCode` (optional, simple text)
 * - `begripBegrippenlijst` (required, complex type `VerwijzingGegevens`)
 *
 * @param reader The [XMLEventReader] to read from.
 * @param startEvent The `START_ELEMENT` for the `begripGegevens` block.
 *
 * @return A fully populated [BegripGegevens] instance.
 *
 * @throws XMLStreamException If an error occurs while reading from the XML stream.
 * @throws IllegalStateException If required elements (`begripLabel` or
 *         `begripBegrippenlijst`) are missing.
 *
 * After successful return, the reader will be positioned on the matching
 * `END_ELEMENT` of [startEvent].
 */
internal fun getBegripGegevens(reader: XMLEventReader, startEvent: StartElement): BegripGegevens {
    val startEventName = startEvent.name
    var currentEvent: StartElement = startEvent
    var label: String? = null
    var code: String? = null // optional
    var begrippenLijst: VerwijzingGegevens? = null

    while(reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        currentEvent = reader.requireNextTagAsStartElement() // This should be a startEvent. Any other events are handles by sub functions
        /*
         * 3 smaken hier, zouden in deze volgorde langs moeten komen:
         * - begripLabel (String)
         * - begripCode (String, optional)
         * - begripBegrippenlijst (VerwijzingGegevens)
         */
        when(currentEvent.name.localPart){
            BEGRIP_LABEL_TAG -> label = reader.readSimpleElementText(currentEvent)
            BEGRIP_CODE_TAG -> code = reader.readSimpleElementText(currentEvent)
            BEGRIP_BEGRIPPENLIJST_TAG -> begrippenLijst = getVerwijzingGegevens(reader, currentEvent)
        }
    }
    if(label == null || begrippenLijst == null) throw IllegalStateException ("ERROR 0002: BegripGegevens incompleet bij regel ${startEvent.location}")

    return BegripGegevens(label, code, begrippenLijst)
}