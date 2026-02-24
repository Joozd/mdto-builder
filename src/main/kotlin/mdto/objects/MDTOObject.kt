package nl.joozd.mdto.objects

/**
 * @property identificatie Gegevens waarmee het object geïdentificeerd kan worden.
 * @property naam Een betekenisvolle aanduiding waaronder het object bekend is.
 */
interface MDTOObject: MDTONode{
    val identificatie: List<IdentificatieGegevens> // must not be empty
    val naam: String
}