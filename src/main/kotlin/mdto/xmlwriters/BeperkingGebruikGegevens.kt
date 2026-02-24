package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.BeperkingGebruikGegevens
import javax.xml.stream.XMLStreamWriter

fun BeperkingGebruikGegevens.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)
    beperkingGebruikType.emit("beperkingGebruikType", writer)
    beperkingGebruikNadereBeschrijving?.let { writer.writeCharacters(it) }
    for (node in beperkingGebruikDocumentatie){
        node.emit("beperkingGebruikDocumentatie", writer)
    }
    beperkingGebruikTermijn?.emit("beperkingGebruikTermijn", writer)
}