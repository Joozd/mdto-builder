package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.IdentificatieGegevens
import javax.xml.stream.XMLStreamWriter

/**
 * Emit deze IdentificatieGegevens als XML volgens MDTO-structuur.
 */
internal fun IdentificatieGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    writer.element( "identificatieKenmerk",identificatieKenmerk)

    writer.element("identificatieBron",identificatieBron)

    writer.writeEndElement()
}