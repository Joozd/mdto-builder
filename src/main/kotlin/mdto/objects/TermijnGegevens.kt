package nl.joozd.mdto.objects

import nl.joozd.mdto.types.XsdDateUnion
import nl.joozd.mdto.types.XsdDuration
import java.time.LocalDate

/**
 * Gegevens over de bewaartermijn van een informatieobject.
 */
data class TermijnGegevens(
    val triggerStartLooptijd: BegripGegevens? = null,
    val startdatumLooptijd: LocalDate? = null,
    val looptijd: XsdDuration? = null,
    val einddatum: XsdDateUnion? = null,
) : MDTONode