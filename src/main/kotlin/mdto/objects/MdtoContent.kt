package nl.joozd.mdto.objects

sealed interface MdtoContent: MDTONode{
    val identificatie: List<IdentificatieGegevens> // must not be empty
    val naam: String
}