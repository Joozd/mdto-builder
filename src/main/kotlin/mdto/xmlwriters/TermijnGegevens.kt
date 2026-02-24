package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.TermijnGegevens
import nl.joozd.mdto.types.toXmlString
import java.time.format.DateTimeFormatter
import javax.xml.stream.XMLStreamWriter

fun TermijnGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)
    termijnTriggerStartLooptijd?.emit("termijnTriggerStartLooptijd", writer)
    writer.element("termijnStartdatumLooptijd",termijnStartdatumLooptijd?.format(DateTimeFormatter.ISO_LOCAL_DATE))
    writer.element("termijnLooptijd", termijnLooptijd?.toString())
    writer.element("termijnEinddatum",termijnEinddatum?.toXmlString())
    writer.writeEndElement()
}