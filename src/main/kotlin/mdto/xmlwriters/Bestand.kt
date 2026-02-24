package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.Bestand
import javax.xml.stream.XMLStreamWriter

fun Bestand.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    // identificatie (1..n)
    for (id in identificatie) {
        id.emit("identificatie", writer)
    }

    // naam (verplicht vanuit base)
    writer.element("naam",naam)
    writer.writeEndElement()

    // omvang (verplicht)
    writer.element("omvang",omvang.toString())

    // bestandsformaat (verplicht)
    bestandsformaat.emit("bestandsformaat", writer)

    // checksum (1..n)
    for (cs in checksum) {
        cs.emit("checksum", writer)
    }

    // urlBestand (0..1)
    writer.element("urlBestand",urlBestand?.toString())


    // isRepresentatieVan (verplicht)
    isRepresentatieVan.emit("isRepresentatieVan", writer)

    writer.writeEndElement()
}