package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.GerelateerdInformatieobjectGegevens
import javax.xml.stream.XMLStreamWriter

fun GerelateerdInformatieobjectGegevens.emit(
    name: String,
    writer: XMLStreamWriter
) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    gerelateerdInformatieobjectVerwijzing
        .emit("gerelateerdInformatieobjectVerwijzing", writer)

    gerelateerdInformatieobjectTypeRelatie
        .emit("gerelateerdInformatieobjectTypeRelatie", writer)

    writer.writeEndElement()
}