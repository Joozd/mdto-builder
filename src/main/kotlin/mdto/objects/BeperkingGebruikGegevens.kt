package nl.joozd.mdto.objects

/**
 * Een beperking die gesteld is aan het gebruik van het informatieobject.
 *
 * @property beperkingGebruikType Typering van de beperking. Op grond waarvan bepaald kan worden wie toegang heeft tot
 *  het informatieobject en welke voorwaarden op het gebruik van toepassing zijn.
 * @property beperkingGebruikNadereBeschrijving Nadere beschrijving van de beperking op het gebruik.
 *  Als aanvulling op beperkingGebruikType.
 * @property beperkingGebruikDocumentatie Verwijzing naar een tekstdocument waarin een nadere beschrijving van de
 *  beperking is opgenomen.
 * @property beperkingGebruikTermijn De termijn waarbinnen de beperking op het gebruik van toepassing is.
 */
data class BeperkingGebruikGegevens(
    val beperkingGebruikType: BegripGegevens,
    val beperkingGebruikNadereBeschrijving: String? = null,
    val beperkingGebruikDocumentatie: List<VerwijzingGegevens> = emptyList(),
    val beperkingGebruikTermijn: TermijnGegevens? = null,
) : MDTONode