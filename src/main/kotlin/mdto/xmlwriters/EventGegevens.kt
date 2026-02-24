package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.EventGegevens
import nl.joozd.mdto.types.toXmlString
import javax.xml.stream.XMLStreamWriter

fun EventGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    eventType.emit("eventType", writer)
    writer.element("eventTijd",eventTijd?.toXmlString())
    eventVerantwoordelijkeActor?.emit("eventVerantwoordelijkeActor", writer)
    writer.element("eventResultaat",eventResultaat)

    writer.writeEndElement()
}