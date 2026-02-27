package nl.joozd.parsing

import nl.joozd.mdto.objects.*
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import javax.xml.stream.XMLEventReader
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val IDENTIFICATIE_TAG = "identificatie"
private const val NAAM_TAG = "naam"

internal fun getInformatieobject(
    reader: XMLEventReader,
    startEvent: StartElement,
): Informatieobject {

    val startEventName = startEvent.name

    // --- base ---
    val identificaties = mutableListOf<IdentificatieGegevens>()
    var naam: String? = null

    // --- extension ---
    var aggregatieniveau: BegripGegevens? = null
    val classificaties = mutableListOf<BegripGegevens>()
    val trefwoorden = mutableListOf<String>()
    val omschrijvingen = mutableListOf<String>()
    val raadpleeglocaties = mutableListOf<RaadpleeglocatieGegevens>()
    val dekkingenInTijd = mutableListOf<DekkingInTijdGegevens>()
    val dekkingenInRuimte = mutableListOf<VerwijzingGegevens>()
    val taal = mutableListOf<String>()
    val events = mutableListOf<EventGegevens>()

    var waardering: BegripGegevens? = null
    var bewaartermijn: TermijnGegevens? = null
    var informatiecategorie: BegripGegevens? = null

    val isOnderdeelVan = mutableListOf<VerwijzingGegevens>()
    val bevatOnderdeel = mutableListOf<VerwijzingGegevens>()
    val heeftRepresentatie = mutableListOf<VerwijzingGegevens>()
    val aanvullendeMetagegevens = mutableListOf<VerwijzingGegevens>()
    val gerelateerd = mutableListOf<GerelateerdInformatieobjectGegevens>()
    val archiefvormers = mutableListOf<VerwijzingGegevens>()
    val betrokkenen = mutableListOf<BetrokkeneGegevens>()

    var activiteit: VerwijzingGegevens? = null
    val beperkingenGebruik = mutableListOf<BeperkingGebruikGegevens>()

    var currentEvent: XMLEvent = startEvent

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        println("currentEvent: $currentEvent")
        val nextEvent = reader.nextTag()
        if(nextEvent.isEndElement) {
            currentEvent = nextEvent
            continue
        }
        currentEvent = nextEvent.asStartElement()
        when (currentEvent.name.localPart) {
            IDENTIFICATIE_TAG ->
                identificaties += getIdentificatieGegevens(reader, currentEvent)

            NAAM_TAG -> naam = reader.readSimpleElementText(currentEvent)

            "aggregatieniveau" ->
                aggregatieniveau = getBegripGegevens(reader, currentEvent)

            "classificatie" ->
                classificaties += getBegripGegevens(reader, currentEvent)

            "trefwoord" ->
                trefwoorden += reader.readSimpleElementText(currentEvent)

            "omschrijving" ->
                omschrijvingen += reader.readSimpleElementText(currentEvent)

            "raadpleeglocatie" ->
                raadpleeglocaties += getRaadpleeglocatieGegevens(reader, currentEvent)

            "dekkingInTijd" ->
                dekkingenInTijd += getDekkingInTijdGegevens(reader, currentEvent)

            "dekkingInRuimte" ->
                dekkingenInRuimte += getVerwijzingGegevens(reader, currentEvent)

            "taal" ->
                taal += reader.readSimpleElementText(currentEvent)

            "event" ->
                events += getEventGegevens(reader, currentEvent)

            "waardering" ->
                waardering = getBegripGegevens(reader, currentEvent)

            "bewaartermijn" ->
                bewaartermijn = getTermijnGegevens(reader, currentEvent)

            "informatiecategorie" ->
                informatiecategorie = getBegripGegevens(reader, currentEvent)

            "isOnderdeelVan" ->
                isOnderdeelVan += getVerwijzingGegevens(reader, currentEvent)

            "bevatOnderdeel" ->
                bevatOnderdeel += getVerwijzingGegevens(reader, currentEvent)

            "heeftRepresentatie" ->
                heeftRepresentatie += getVerwijzingGegevens(reader, currentEvent)

            "aanvullendeMetagegevens" ->
                aanvullendeMetagegevens += getVerwijzingGegevens(reader, currentEvent)

            "gerelateerdInformatieobject" ->
                gerelateerd += getGerelateerdInformatieobjectGegevens(reader, currentEvent)

            "archiefvormer" ->
                archiefvormers += getVerwijzingGegevens(reader, currentEvent)

            "betrokkene" ->
                betrokkenen += getBetrokkeneGegevens(reader, currentEvent)

            "activiteit" ->
                activiteit = getVerwijzingGegevens(reader, currentEvent)

            "beperkingGebruik" ->
                beperkingenGebruik += getBeperkingGebruikGegevens(reader, currentEvent)
        }
    }

    if (naam == null ||
        waardering == null ||
        archiefvormers.isEmpty() ||
        beperkingenGebruik.isEmpty()
    ) {
        throw IllegalStateException(
            "Informatieobject incompleet bij regel ${startEvent.location}"
        )
    }

    return Informatieobject(
        identificatie = identificaties,
        naam = naam,
        aggregatieniveau = aggregatieniveau,
        classificatie = classificaties,
        trefwoord = trefwoorden,
        omschrijving = omschrijvingen,
        raadpleeglocatie = raadpleeglocaties,
        dekkingInTijd = dekkingenInTijd,
        dekkingInRuimte = dekkingenInRuimte,
        taal = taal,
        event = events,
        waardering = waardering,
        bewaartermijn = bewaartermijn,
        informatiecategorie = informatiecategorie,
        isOnderdeelVan = isOnderdeelVan,
        bevatOnderdeel = bevatOnderdeel,
        heeftRepresentatie = heeftRepresentatie,
        aanvullendeMetagegevens = aanvullendeMetagegevens,
        gerelateerdInformatieobject = gerelateerd,
        archiefvormer = archiefvormers,
        betrokkene = betrokkenen,
        activiteit = activiteit,
        beperkingGebruik = beperkingenGebruik,
    )
}