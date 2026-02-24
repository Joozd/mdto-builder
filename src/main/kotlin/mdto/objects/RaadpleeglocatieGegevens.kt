package nl.joozd.mdto.objects

import java.net.URI

/**
 * @property raadpleeglocatieFysiek Actuele fysieke locatie waar het informatieobject ter inzage ligt.
 * @property raadpleeglocatieOnline Actuele online raadpleeglocatie(s) voor inzage.
 */
data class RaadpleeglocatieGegevens(
    val raadpleeglocatieFysiek: List<VerwijzingGegevens> = emptyList(),
    val raadpleeglocatieOnline: List<URI> = emptyList(),
) : MDTONode