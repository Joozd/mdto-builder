package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.ChecksumGegevens
import java.time.format.DateTimeFormatter
import javax.xml.stream.XMLStreamWriter

fun ChecksumGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    checksumAlgoritme.emit("checksumAlgoritme", writer)

    writer.element("checksumWaarde",checksumWaarde)

    writer.element("checksumDatum",
        checksumDatum.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    )

    writer.writeEndElement()
}