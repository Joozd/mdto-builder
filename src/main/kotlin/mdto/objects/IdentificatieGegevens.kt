package nl.joozd.mdto.objects

/**
 * @param identificatieKenmerk Een kenmerk waarmee een object geïdentificeerd kan worden.
 * @param identificatieBron Herkomst van het kenmerk.
 */
data class IdentificatieGegevens(
    val identificatieKenmerk: String,
    val identificatieBron: String,
    ): MDTONode