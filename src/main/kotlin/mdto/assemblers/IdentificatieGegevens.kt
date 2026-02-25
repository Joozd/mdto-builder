package nl.joozd.mdto.assemblers

import nl.joozd.mdto.objects.IdentificatieGegevens
import nl.joozd.sources.PresetValues
import nl.joozd.sources.Sources
import nl.joozd.utils.ErrorLogger

/**
 * Maak identificatiegegevens aan de hand van [sources]
 * Dit kan zowel een Bestand als een InformatieObject (map) zijn.
 */
fun assembleIdentificatieGegevensUitBestandsNaam(sources: Sources, errorLogger: ErrorLogger): List<IdentificatieGegevens> {
    val bron = sources.presetValues[PresetValues.IDENTIFICATIE_BRON] ?: errorLogger.add("assembleInformatieObjectIdentificatieGegevens", "Geen IDENTIFICATIE_BRON in presetValues")
    val kenmerken = sources.fileIdentification.identificatieKenmerk

    return kenmerken.map { IdentificatieGegevens( it, bron) }
}




