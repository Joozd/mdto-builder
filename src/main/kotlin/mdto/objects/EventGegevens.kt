package nl.joozd.mdto.objects

import nl.joozd.mdto.types.XsdDateOrDateTimeUnion

/**
 * Gebeurtenis die heeft plaatsgevonden met betrekking tot het ontstaan, wijzigen, vernietigen en beheer van het
 * informatieobject en de bijbehorende metagegevens.
 *
 * @property eventType Aanduiding van het type event.
 * @property eventTijd Het tijdstip waarop het event heeft plaatsgevonden.
 * @property eventVerantwoordelijkeActor De actor die verantwoordelijk was voor de gebeurtenis.
 * @property eventResultaat Beschrijving van het resultaat van het event voor zover relevant voor de duurzame
 *  toegankelijkheid van het informatieobject.
 */
data class EventGegevens(
    val eventType: BegripGegevens,
    val eventTijd: XsdDateOrDateTimeUnion? = null,
    val eventVerantwoordelijkeActor: VerwijzingGegevens? = null,
    val eventResultaat: String? = null,
) : MDTONode