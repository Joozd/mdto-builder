package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.TermijnGegevens
import nl.joozd.mdto.types.toXmlString
import java.time.format.DateTimeFormatter
import javax.xml.stream.XMLStreamWriter

fun TermijnGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)
    triggerStartLooptijd?.emit("termijnTriggerStartLooptijd", writer)
    writer.element("termijnStartdatumLooptijd",startdatumLooptijd?.format(DateTimeFormatter.ISO_LOCAL_DATE))
    writer.element("termijnLooptijd", looptijd?.toString())
    writer.element("termijnEinddatum",einddatum?.toXmlString())
    writer.writeEndElement()
}