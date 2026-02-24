package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.BetrokkeneGegevens
import javax.xml.stream.XMLStreamWriter

fun BetrokkeneGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    betrokkeneTypeRelatie.emit("betrokkeneTypeRelatie", writer)
    betrokkeneActor.emit("betrokkeneActor", writer)

    writer.writeEndElement()
}