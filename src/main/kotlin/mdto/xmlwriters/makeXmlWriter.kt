package nl.joozd.mdto.xmlwriters

import com.sun.xml.txw2.output.IndentingXMLStreamWriter
import java.io.OutputStream
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamWriter

/**
 * Standard implementation of (pretty printed) XML writer.
 */
internal fun makeXmlWriter(
    outputStream: OutputStream,
    factory: XMLOutputFactory = XMLOutputFactory.newFactory(),
    encoding: String = "UTF-8"
): XMLStreamWriter {
    val base = factory.createXMLStreamWriter(outputStream, encoding)
    return IndentingXMLStreamWriter(base)
}

