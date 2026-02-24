package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.Bestand
import nl.joozd.mdto.objects.Informatieobject
import nl.joozd.mdto.objects.MDTORoot
import nl.joozd.mdto.objects.MdtoBaseClassContent
import javax.xml.stream.XMLStreamWriter

internal fun MDTORoot.emit(writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, MDTORoot.OBJECT_NAME)
    when(content){
        is Bestand -> content.emit("bestand", writer)
        is Informatieobject -> content.emit("informatieobject", writer)
    }
    writer.writeEndElement()
}