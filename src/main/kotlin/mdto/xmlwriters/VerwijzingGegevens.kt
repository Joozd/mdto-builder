package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.VerwijzingGegevens
import javax.xml.stream.XMLStreamWriter

fun VerwijzingGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)
    writer.element("verwijzingNaam", verwijzingNaam)
    verwijzingIdentificatie?.emit("verwijzingIdentificatie", writer)
    writer.writeEndElement()
}