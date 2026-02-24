package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.Bestand
import nl.joozd.mdto.objects.Informatieobject
import nl.joozd.mdto.objects.MDTORoot
import javax.xml.stream.XMLStreamWriter

internal fun MDTORoot.emit(writer: XMLStreamWriter) {
    writer.writeStartDocument("UTF-8", "1.0")

    // Default namespace instellen
    writer.setDefaultNamespace(MDTOXmlConstants.NS)

    writer.writeStartElement(
        MDTOXmlConstants.NS,
        MDTOXmlConstants.ROOT_ELEMENT
    )

    // xmlns="..."
    writer.writeDefaultNamespace(MDTOXmlConstants.NS)

    // xmlns:xsi="..."
    writer.writeNamespace(
        MDTOXmlConstants.XSI_PREFIX,
        MDTOXmlConstants.XSI_NS
    )

    // xsi:schemaLocation="..."
    writer.writeAttribute(
        MDTOXmlConstants.XSI_NS,
        "schemaLocation",
        MDTOXmlConstants.SCHEMA_LOCATION
    )

    when (content) {
        is Bestand -> content.emit("bestand", writer)
        is Informatieobject -> content.emit("informatieobject", writer)
    }

    writer.writeEndElement()
    writer.writeEndDocument()
}