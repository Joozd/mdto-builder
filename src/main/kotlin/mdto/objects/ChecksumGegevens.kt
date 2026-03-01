package nl.joozd.mdto.objects

import nl.joozd.mdto.types.XsdDateTimeUnion

/**
 * @property algoritme Naam van het checksum algoritme dat is gebruikt om de checksum te maken.
 * @property waarde De waarde van de checksum.
 * @property datum Datum waarop de checksum is gemaakt.
 */
data class ChecksumGegevens(
    val algoritme: BegripGegevens,
    val waarde: String,
    val datum: XsdDateTimeUnion
): MDTONode