package nl.joozd.mdto.objects

/**
 * Relatie met een ander informatieobject.
 */
data class GerelateerdInformatieobjectGegevens(
    val verwijzing: VerwijzingGegevens,
    val typeRelatie: BegripGegevens,
) : MDTONode