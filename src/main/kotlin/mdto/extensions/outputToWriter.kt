package nl.joozd.mdto.extensions

import nl.joozd.mdto.objects.MDTORoot
import nl.joozd.mdto.objects.MdtoContent
import nl.joozd.mdto.xmlwriters.emit
import javax.xml.stream.XMLStreamWriter

/**
 * Outputs the content of this object to [writer]
 * This can be used when the MDTO XML needs to be embedded into another XML stream, for example into an OPEX file.
 */
fun MdtoContent.outputToWriter(writer: XMLStreamWriter){
    val root = MDTORoot(this)
    root.emit(writer)
}