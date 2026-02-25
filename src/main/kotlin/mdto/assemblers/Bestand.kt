package nl.joozd.mdto.assemblers

import nl.joozd.mdto.objects.Bestand
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.sources.Sources
import nl.joozd.utils.ErrorLogger

/**
 * Assemble a "Bestand" from data in [sources].
 * Any errors (e.g. incomplete or invalid data) will be logged to [errorLogger]
 */
fun assembleBestand(sources: Sources, errorLogger: ErrorLogger): Bestand {
    val identificatie = assembleIdentificatieGegevensUitBestandsNaam(sources, errorLogger)
    val naam = sources.fileIdentification.naam
    val omvang = berekenOmvang(sources, errorLogger)
    val bestandsFormaat = maakBestandsFormaat(sources, errorLogger)
    val checksum = listOf(sha256Checksum(sources, errorLogger))
    val isRepresentatieVan = sources.fileIdentification.isOnderdeelVan.map { VerwijzingGegevens(it) }.also { if(it.size != 1)
    errorLogger.add ("assembleBestand", "Bestand dossier in filenaam (${sources.fileIdentification.naam}) wijkt af van directorynaam ($it)")
    }.first()

    return Bestand(
        identificatie = identificatie,
        naam = naam,
        omvang = omvang,
        bestandsformaat = bestandsFormaat,
        checksum = checksum,
        isRepresentatieVan = isRepresentatieVan
    )
}

private fun berekenOmvang(sources: Sources, errorLogger: ErrorLogger): Long =
    sources.fileIdentification.fileSize ?: 0L.also{
        errorLogger.add("assembleBestand.berekenOmvang", "Kan geen omvang berekenen van een map (${sources.fileIdentification.naam})")
    }


