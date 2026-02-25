package nl.joozd.mdto.assemblers

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.Informatieobject
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.sources.Sources
import nl.joozd.utils.ErrorLogger

private const val AGGREGATIE_NIVEAU_LABEL = "Archiefstuk"
private const val AGGREGATIE_NIVEAU_LIJST = "Begrippenlijst Aggregatieniveaus MDTO"

/**
 * Assemble an "Informatieobject" from data in [sources].
 * Any errors (e.g. incomplete or invalid data) will be logged to [errorLogger]
 */
fun assembleInformatieObject(sources: Sources, errorLogger: ErrorLogger): Informatieobject {
    val identificatie = assembleIdentificatieGegevensUitBestandsNaam(sources, errorLogger)
    val naam = sources.fileIdentification.naam

    val aggregatieniveau = BegripGegevens(AGGREGATIE_NIVEAU_LABEL, begripBegrippenlijst = VerwijzingGegevens(AGGREGATIE_NIVEAU_LIJST))
    val raadpleeglocatie = listOf(assembleRaadpleeglocatieGegevens(sources, errorLogger))
    val event = listOf(
        assembleDigitaliseringEventGegevens(sources, errorLogger),
        assembleImportEventGegevens(sources, errorLogger)
    )
    val waardering = BegripGegevens("Tijdelijk te bewaren", "V", begripBegrippenlijst = VerwijzingGegevens("Begrippenlijst Waarderingen MDTO"))

    val isOnderdeelVan = sources.fileIdentification.isOnderdeelVan.map { VerwijzingGegevens(it) }

    // mag wel hoeft niet slaan we verder over
    val archiefVormer = listOf(assembleArchiefVormer(sources, errorLogger))
    val beperkingGebruik = listOf(assembleBeperkingGebruikGegevens(sources, errorLogger))


    return Informatieobject(
        identificatie = identificatie,
        naam = naam,
        aggregatieniveau = aggregatieniveau,
        raadpleeglocatie = raadpleeglocatie,
        event = event,
        waardering = waardering,
        isOnderdeelVan = isOnderdeelVan,
        archiefvormer = archiefVormer,
        beperkingGebruik = beperkingGebruik
    )
}