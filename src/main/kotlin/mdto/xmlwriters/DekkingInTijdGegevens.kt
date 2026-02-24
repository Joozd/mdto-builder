package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.DekkingInTijdGegevens
import nl.joozd.mdto.types.toXmlString
import javax.xml.stream.XMLStreamWriter

fun DekkingInTijdGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    dekkingInTijdType.emit("dekkingInTijdType", writer)

    writer.element("dekkingInTijdBegindatum",dekkingInTijdBegindatum.toXmlString())
    writer.element("dekkingInTijdEinddatum",dekkingInTijdEinddatum?.toXmlString())

    writer.writeEndElement()
}