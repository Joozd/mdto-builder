package nl.joozd.mdto.xmlwriters

import javax.xml.stream.XMLStreamWriter

/**
 * Writes an element if [value] is not null
 */
internal fun XMLStreamWriter.element(name: String, value: String?) {
    if (value != null) {
        writeStartElement(MDTOXmlConstants.NS, name)
        writeCharacters(value)
        writeEndElement()
    }
}