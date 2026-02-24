package nl.joozd.mdto.objects

/**
 * Beperking die gesteld is aan het gebruik van het informatieobject.
 */
data class BeperkingGebruikGegevens(
    val beperkingGebruikType: BegripGegevens,
    val beperkingGebruikNadereBeschrijving: String? = null,
    val beperkingGebruikDocumentatie: List<VerwijzingGegevens> = emptyList(),
    val beperkingGebruikTermijn: TermijnGegevens? = null,
) : MDTONode