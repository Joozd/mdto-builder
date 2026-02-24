package nl.joozd.mdto.objects

/**
 * Persoon of organisatie die betrokken is bij het informatieobject.
 */
data class BetrokkeneGegevens(
    val betrokkeneTypeRelatie: BegripGegevens,
    val betrokkeneActor: VerwijzingGegevens,
) : MDTONode