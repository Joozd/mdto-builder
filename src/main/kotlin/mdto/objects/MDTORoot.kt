package nl.joozd.mdto.objects

data class MDTORoot(val content: MdtoBaseClassContent): MDTONode{
    companion object{
        const val OBJECT_NAME = "MDTO"
    }
}