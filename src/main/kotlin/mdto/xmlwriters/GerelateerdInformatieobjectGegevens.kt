package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.GerelateerdInformatieobjectGegevens
import javax.xml.stream.XMLStreamWriter

fun GerelateerdInformatieobjectGegevens.emit(
    name: String,
    writer: XMLStreamWriter
) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    verwijzing
        .emit("gerelateerdInformatieobjectVerwijzing", writer)

    typeRelatie
        .emit("gerelateerdInformatieobjectTypeRelatie", writer)

    writer.writeEndElement()
}