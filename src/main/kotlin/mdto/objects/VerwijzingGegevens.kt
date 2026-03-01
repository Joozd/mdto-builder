package nl.joozd.mdto.objects

/**
 * @property naam De naam van het object waarnaar verwezen wordt.
 * @property identificatie De identificatie van het object waarnaar verwezen wordt.
 */
data class VerwijzingGegevens(
    val naam: String,
    val identificatie: IdentificatieGegevens? = null
): MDTONode