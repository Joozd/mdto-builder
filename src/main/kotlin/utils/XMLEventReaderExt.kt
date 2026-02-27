package nl.joozd.utils

import nl.joozd.exceptions.NotAStartElementException
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.EndElement
import javax.xml.stream.events.StartElement

/**
 * Guarantees a StartElement, or throws.
 * @Throws XMLStreamException – if there is an error with the underlying XML.
 * @throws NotAStartElementException if next element is not a start element.
 */
fun XMLEventReader.requireNextTagAsStartElement(): StartElement{
    val next = nextTag()
    if(!next.isStartElement) throw NotAStartElementException("ERROR 0001: Next element must be a start element, it is ${next.eventType}, at line ${next.location}")
    return next.asStartElement()
}



/**
 * Guarantees an EndElement, or throws.
 * @Throws XMLStreamException – if there is an error with the underlying XML.
 * @throws NotAStartElementException if next element is not a start element.
 */
fun XMLEventReader.requireNextTagAsEndElement(): EndElement{
    val next = nextTag()
    if(!next.isEndElement) throw NotAStartElementException("ERROR 0001: Next element must be a start element, it is ${next.eventType}, at line ${next.location}")
    return next.asEndElement()
}

/**
 * Reads the text content of a simple (element-only) XML element.
 *
 * This function assumes the reader is currently positioned immediately after
 * the `START_ELEMENT` represented by [start]. It will consume events until the
 * matching `END_ELEMENT` is encountered.
 *
 * All consecutive `CHARACTERS` events are concatenated and returned as a
 * trimmed `String`.
 *
 * @param start The `StartElement` that marks the beginning of the element
 *              whose text content should be read.
 *
 * @return The trimmed text content inside the element.
 *
 * @throws XMLStreamException If an error occurs while reading from the stream.
 * @throws IllegalStateException If a nested `START_ELEMENT` is encountered,
 *         since this helper is intended only for simple text-only elements.
 *
 * After successful return, the reader will be positioned on the matching
 * `END_ELEMENT` for [start].
 */
fun XMLEventReader.readSimpleElementText(start: StartElement): String = buildString {
    while (hasNext()) {
        val event = nextEvent() // will throw on unexpected end
        when {
            event.isCharacters -> append(event.asCharacters().data)
            event.isEndElement && event.asEndElement().name == start.name -> break
            event.isStartElement -> error("Unexpected nested element inside ${start.name}")
        }
    }
}.trim()