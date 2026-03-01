package nl.joozd.mdto.objects

/**
 * @param kenmerk Een kenmerk waarmee een object geïdentificeerd kan worden.
 * @param bron Herkomst van het kenmerk.
 */
data class IdentificatieGegevens(
    val kenmerk: String,
    val bron: String,
    ): MDTONode