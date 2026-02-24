package nl.joozd.mdto.objects

/**
 * Relatie met een ander informatieobject.
 */
data class GerelateerdInformatieobjectGegevens(
    val gerelateerdInformatieobjectVerwijzing: VerwijzingGegevens,
    val gerelateerdInformatieobjectTypeRelatie: BegripGegevens,
) : MDTONode