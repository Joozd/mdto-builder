package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.ChecksumGegevens
import nl.joozd.mdto.types.toXmlString
import javax.xml.stream.XMLStreamWriter

fun ChecksumGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    algoritme.emit("checksumAlgoritme", writer)

    writer.element("checksumWaarde",waarde)

    writer.element("checksumDatum",
        datum.toXmlString()
    )

    writer.writeEndElement()
}