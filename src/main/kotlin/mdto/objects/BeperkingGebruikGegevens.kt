package nl.joozd.mdto.objects

/**
 * Een beperking die gesteld is aan het gebruik van het informatieobject.
 *
 * @property type Typering van de beperking. Op grond waarvan bepaald kan worden wie toegang heeft tot
 *  het informatieobject en welke voorwaarden op het gebruik van toepassing zijn.
 * @property nadereBeschrijving Nadere beschrijving van de beperking op het gebruik.
 *  Als aanvulling op beperkingGebruikType.
 * @property documentatie Verwijzing naar een tekstdocument waarin een nadere beschrijving van de
 *  beperking is opgenomen.
 * @property termijn De termijn waarbinnen de beperking op het gebruik van toepassing is.
 */
data class BeperkingGebruikGegevens(
    val type: BegripGegevens,
    val nadereBeschrijving: String? = null,
    val documentatie: List<VerwijzingGegevens> = emptyList(),
    val termijn: TermijnGegevens? = null,
) : MDTONode