package nl.joozd.mdto.objects

import nl.joozd.mdto.types.XsdDateOrDateTimeUnion

/**
 * Gebeurtenis met betrekking tot het informatieobject.
 */
data class EventGegevens(
    val eventType: BegripGegevens,
    val eventTijd: XsdDateOrDateTimeUnion? = null,
    val eventVerantwoordelijkeActor: VerwijzingGegevens? = null,
    val eventResultaat: String? = null,
) : MDTONode