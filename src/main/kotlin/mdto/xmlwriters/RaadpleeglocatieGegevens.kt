package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.RaadpleeglocatieGegevens
import javax.xml.stream.XMLStreamWriter

fun RaadpleeglocatieGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    for (fysiek in fysiek) {
        fysiek.emit("raadpleeglocatieFysiek", writer)
    }

    for (online in online) {
        writer.element("raadpleeglocatieOnline", online.toString())
    }

    writer.writeEndElement()
}