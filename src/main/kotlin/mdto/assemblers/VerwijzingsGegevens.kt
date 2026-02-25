package nl.joozd.mdto.assemblers

import nl.joozd.mdto.objects.IdentificatieGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.sources.PresetValues
import nl.joozd.sources.Sources
import nl.joozd.utils.ErrorLogger

fun assembleArchiefVormer(sources: Sources, errorLogger: ErrorLogger): VerwijzingGegevens {
    val naam = sources.presetValues[PresetValues.ARCHIEFVORMER_NAAM] ?: errorLogger.add("assembleArchiefVormer", "geen ARCHIEFVORMER_NAAM in presets")
    val kenmerk = sources.presetValues[PresetValues.ARCHIEFVORMER_KENMERK] ?: errorLogger.add("assembleArchiefVormer", "geen ARCHIEFVORMER_KENMERK in presets")
    val bron = sources.presetValues[PresetValues.ARCHIEFVORMER_BRON] ?: errorLogger.add("assembleArchiefVormer", "geen ARCHIEFVORMER_BRON in presets")

    return VerwijzingGegevens(naam, IdentificatieGegevens(kenmerk, bron))
}