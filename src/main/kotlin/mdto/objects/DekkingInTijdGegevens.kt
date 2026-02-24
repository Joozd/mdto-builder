package nl.joozd.mdto.objects

import nl.joozd.mdto.types.XsdDateUnion

/**
 * Tijdstip of periode waarop de inhoud van het informatieobject betrekking heeft.
 *
 * @property dekkingInTijdType Nadere typering van het tijdstip of de periode.
 * @property dekkingInTijdBegindatum Begindatum (of enkelvoudige datum).
 * @property dekkingInTijdEinddatum Einddatum van de periode (optioneel).
 */
data class DekkingInTijdGegevens(
    val dekkingInTijdType: BegripGegevens,
    val dekkingInTijdBegindatum: XsdDateUnion,
    val dekkingInTijdEinddatum: XsdDateUnion? = null,
) : MDTONode