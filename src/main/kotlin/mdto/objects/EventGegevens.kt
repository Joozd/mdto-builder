package nl.joozd.mdto.objects

import nl.joozd.mdto.types.XsdDateOrDateTimeUnion

/**
 * Gebeurtenis die heeft plaatsgevonden met betrekking tot het ontstaan, wijzigen, vernietigen en beheer van het
 * informatieobject en de bijbehorende metagegevens.
 *
 * @property type Aanduiding van het type event.
 * @property tijd Het tijdstip waarop het event heeft plaatsgevonden.
 * @property verantwoordelijkeActor De actor die verantwoordelijk was voor de gebeurtenis.
 * @property resultaat Beschrijving van het resultaat van het event voor zover relevant voor de duurzame
 *  toegankelijkheid van het informatieobject.
 */
data class EventGegevens(
    val type: BegripGegevens,
    val tijd: XsdDateOrDateTimeUnion? = null,
    val verantwoordelijkeActor: VerwijzingGegevens? = null,
    val resultaat: String? = null,
) : MDTONode