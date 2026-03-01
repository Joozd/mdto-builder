package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.BeperkingGebruikGegevens
import javax.xml.stream.XMLStreamWriter

fun BeperkingGebruikGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)
    type.emit("beperkingGebruikType", writer)
    writer.element("beperkingGebruikNadereBeschrijving",nadereBeschrijving)
    for (node in documentatie){
        node.emit("beperkingGebruikDocumentatie", writer)
    }
    termijn?.emit("beperkingGebruikTermijn", writer)

    writer.writeEndElement()
}