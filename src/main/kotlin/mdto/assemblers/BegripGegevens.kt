package nl.joozd.mdto.assemblers

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.sources.Sources
import nl.joozd.utils.ErrorLogger

private const val MIME_TYPE_BRON = "Internet Assigned Numbers Authority"

fun maakBestandsFormaat(sources: Sources, errorLogger: ErrorLogger): BegripGegevens{
    val begrippenLijst = VerwijzingGegevens(MIME_TYPE_BRON)
    val label = sources.fileIdentification.mimeType ?:
        errorLogger.add("maakBestandsFormaat", "Kan geen bestandsformaat maken van (${sources.fileIdentification.naam}) - waarschijnlijk is dit een directory en geen bestand.")

    return BegripGegevens(label, begripBegrippenlijst = begrippenLijst)
}


