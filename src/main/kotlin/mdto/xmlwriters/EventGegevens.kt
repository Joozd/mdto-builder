package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.EventGegevens
import nl.joozd.mdto.types.toXmlString
import javax.xml.stream.XMLStreamWriter

fun EventGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    type.emit("eventType", writer)
    writer.element("eventTijd",tijd?.toXmlString())
    verantwoordelijkeActor?.emit("eventVerantwoordelijkeActor", writer)
    writer.element("eventResultaat",resultaat)

    writer.writeEndElement()
}