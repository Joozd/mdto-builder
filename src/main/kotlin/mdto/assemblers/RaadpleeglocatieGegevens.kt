package nl.joozd.mdto.assemblers

import nl.joozd.mdto.objects.RaadpleeglocatieGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.sources.PresetValues
import nl.joozd.sources.Sources
import nl.joozd.utils.ErrorLogger

fun assembleRaadpleeglocatieGegevens(sources: Sources, errorLogger: ErrorLogger): RaadpleeglocatieGegevens{
    val raadpleegLocatieFysiek = sources.presetValues[PresetValues.RAADPLEEGLOCATIE_FYSIEK] ?: errorLogger.add("assembleRaadpleeglocatieGegevens", "PresetValues.RAADPLEEGLOCATIE_FYSIEK niet gevonden in presetValues")

    return RaadpleeglocatieGegevens(raadpleeglocatieFysiek = listOf(VerwijzingGegevens(raadpleegLocatieFysiek)))
}