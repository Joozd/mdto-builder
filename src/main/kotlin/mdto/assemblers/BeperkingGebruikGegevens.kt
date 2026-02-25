package nl.joozd.mdto.assemblers

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.BeperkingGebruikGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.sources.Sources
import nl.joozd.utils.ErrorLogger

fun assembleBeperkingGebruikGegevens(sources: Sources, errorLogger: ErrorLogger): BeperkingGebruikGegevens {
    val label = "Nader te bepalen"
    val bron = "https://www.nationaalarchief.nl/archiveren/mdto/begrippenlijst-metagegevensschema#collapse-102684"
    val type = BegripGegevens(label, begripBegrippenlijst = VerwijzingGegevens(bron))

    return BeperkingGebruikGegevens(type)
}