package nl.joozd.parsing

import nl.joozd.mdto.objects.MdtoContent
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.requireNextTagAsEndElement
import nl.joozd.utils.requireNextTagAsStartElement
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement

private const val INFORMATIEOBJECT_TAG = "informatieobject"
private const val BESTAND_TAG = "bestand"

/**
 * Parses an `mdtoType` element (choice of `informatieobject` or `bestand`).
 *
 * The reader must be positioned on the `START_ELEMENT` represented by [startEvent].
 * Consumes the chosen child element and the matching `END_ELEMENT` for [startEvent].
 */
internal fun getMdtoBaseClassContent(
    reader: XMLEventReader,
    startEvent: StartElement,
): MdtoContent {

    val startEventName = startEvent.name

    val childStart = reader.requireNextTagAsStartElement()

    val result: MdtoContent = when (childStart.name.localPart) {

        INFORMATIEOBJECT_TAG ->
            getInformatieobject(reader, childStart) // returns Informatieobject

        BESTAND_TAG ->
            getBestand(reader, childStart) // returns Bestand

        else -> throw IllegalStateException(
            "Onbekend element binnen mdtoType bij regel ${childStart.location}"
        )
    }

    val endEvent = reader.requireNextTagAsEndElement()
    if (!endEvent.isEndEventFor(startEventName)) {
        throw IllegalStateException(
            "mdtoType bevat meer dan één element bij regel ${startEvent.location}"
        )
    }

    return result
}