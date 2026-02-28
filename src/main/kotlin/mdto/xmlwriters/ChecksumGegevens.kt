package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.ChecksumGegevens
import nl.joozd.mdto.types.toXmlString
import javax.xml.stream.XMLStreamWriter

fun ChecksumGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    checksumAlgoritme.emit("checksumAlgoritme", writer)

    writer.element("checksumWaarde",checksumWaarde)

    writer.element("checksumDatum",
        checksumDatum.toXmlString()
    )

    writer.writeEndElement()
}