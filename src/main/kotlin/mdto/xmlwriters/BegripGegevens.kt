package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.BegripGegevens
import javax.xml.stream.XMLStreamWriter


fun BegripGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    writer.element("begripLabel", begripLabel)
    writer.element("begripCode", begripCode)

    begripBegrippenlijst.emit("begripBegrippenlijst", writer)

    writer.writeEndElement()
}