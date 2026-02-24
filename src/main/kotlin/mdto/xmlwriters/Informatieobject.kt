package nl.joozd.mdto.xmlwriters

import nl.joozd.mdto.objects.Informatieobject
import javax.xml.stream.XMLStreamWriter

fun Informatieobject.emit(name: String, writer: XMLStreamWriter) {
    writer.writeStartElement(MDTOXmlConstants.NS, name)

    for (id in identificatie) {
        id.emit("identificatie", writer)
    }

    writer.element("naam",naam)

    aggregatieniveau?.emit("aggregatieniveau", writer)

    for (c in classificatie) {
        c.emit("classificatie", writer)
    }

    for (t in trefwoord) {
        writer.element( "trefwoord",t)
    }

    for (o in omschrijving) {
        writer.element("omschrijving",o)
    }

    for (r in raadpleeglocatie) {
        r.emit("raadpleeglocatie", writer)
    }

    for (d in dekkingInTijd) {
        d.emit("dekkingInTijd", writer)
    }

    for (d in dekkingInRuimte) {
        d.emit("dekkingInRuimte", writer)
    }

    for (t in taal) {
        writer.element("taal",t)
    }

    for (e in event) {
        e.emit("event", writer)
    }

    waardering.emit("waardering", writer)
    bewaartermijn?.emit("bewaartermijn", writer)
    informatiecategorie?.emit("informatiecategorie", writer)

    for (v in isOnderdeelVan) {
        v.emit("isOnderdeelVan", writer)
    }

    for (v in bevatOnderdeel) {
        v.emit("bevatOnderdeel", writer)
    }

    for (v in heeftRepresentatie) {
        v.emit("heeftRepresentatie", writer)
    }

    for (v in aanvullendeMetagegevens) {
        v.emit("aanvullendeMetagegevens", writer)
    }

    for (g in gerelateerdInformatieobject) {
        g.emit("gerelateerdInformatieobject", writer)
    }

    for (a in archiefvormer) {
        a.emit("archiefvormer", writer)
    }

    for (b in betrokkene) {
        b.emit("betrokkene", writer)
    }

    activiteit?.emit("activiteit", writer)

    for (b in beperkingGebruik) {
        b.emit("beperkingGebruik", writer)
    }

    writer.writeEndElement()
}