package nl.joozd.mdto.objects

import java.net.URI

/**
 * @property fysiek Actuele fysieke locatie waar het informatieobject ter inzage ligt.
 * @property online Actuele online raadpleeglocatie(s) voor inzage.
 */
data class RaadpleeglocatieGegevens(
    val fysiek: List<VerwijzingGegevens> = emptyList(),
    val online: List<URI> = emptyList(),
) : MDTONode