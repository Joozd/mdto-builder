package nl.joozd.mdto.objects

import nl.joozd.mdto.types.XsdDateUnion

/**
 * Tijdstip of periode waarop de inhoud van het informatieobject betrekking heeft.
 *
 * @property type Nadere typering van het tijdstip of de periode.
 * @property begindatum Begindatum (of enkelvoudige datum).
 * @property einddatum Einddatum van de periode (optioneel).
 */
data class DekkingInTijdGegevens(
    val type: BegripGegevens,
    val begindatum: XsdDateUnion,
    val einddatum: XsdDateUnion? = null,
) : MDTONode