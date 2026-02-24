package nl.joozd.mdto.objects

import java.time.OffsetDateTime

/**
 * @property checksumAlgoritme Naam van het checksum algoritme dat is gebruikt om de checksum te maken.
 * @property checksumWaarde De waarde van de checksum.
 * @property checksumDatum Datum waarop de checksum is gemaakt.
 */
data class ChecksumGegevens(
    val checksumAlgoritme: BegripGegevens,
    val checksumWaarde: String,
    val checksumDatum: OffsetDateTime
): MDTONode