package nl.joozd.mdto.objects

/**
 * @property verwijzingNaam De naam van het object waarnaar verwezen wordt.
 * @property verwijzingIdentificatie De identificatie van het object waarnaar verwezen wordt.
 */
data class VerwijzingGegevens(
    val verwijzingNaam: String,
    val verwijzingIdentificatie: IdentificatieGegevens? = null
): MDTONode