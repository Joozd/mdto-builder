package nl.joozd.mdto.objects

import nl.joozd.mdto.types.XsdDateUnion
import nl.joozd.mdto.types.XsdDuration
import java.time.LocalDate

/**
 * Gegevens over de bewaartermijn van een informatieobject.
 */
data class TermijnGegevens(
    val termijnTriggerStartLooptijd: BegripGegevens? = null,
    val termijnStartdatumLooptijd: LocalDate? = null,
    val termijnLooptijd: XsdDuration? = null,
    val termijnEinddatum: XsdDateUnion? = null,
) : MDTONode