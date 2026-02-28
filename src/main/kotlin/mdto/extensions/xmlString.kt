package nl.joozd.mdto.extensions

import nl.joozd.mdto.objects.MDTORoot
import nl.joozd.mdto.objects.MdtoContent
import nl.joozd.mdto.xmlwriters.emit
import nl.joozd.mdto.xmlwriters.makeXmlWriter
import java.io.ByteArrayOutputStream

fun MdtoContent.xmlString(encoding: String = "UTF-8"): String{
    val out = ByteArrayOutputStream()
    out.use {
        val writer = makeXmlWriter(it, encoding = encoding)
        MDTORoot(this).emit(writer)
    }
    return out.toString(encoding)
}