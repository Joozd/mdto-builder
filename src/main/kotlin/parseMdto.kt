package nl.joozd

import nl.joozd.mdto.objects.Bestand
import nl.joozd.mdto.objects.Informatieobject
import nl.joozd.mdto.objects.MDTORoot
import nl.joozd.mdto.objects.MdtoContent
import nl.joozd.parsing.getMdtoBaseClassContent
import nl.joozd.utils.requireNextTagAsStartElement
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamException

private const val MDTO_ROOT_TAG = "MDTO"

/**
 * Parses an MDTO XML file into an [MdtoContent] object.
 * This is either a [Bestand] or an [Informatieobject]
 *
 * This function reads the XML from [path], and expects the first start element to be the MDTO root element (`<MDTO>`).
 *
 * @param path The path to the MDTO XML file.
 * @return The parsed [MDTORoot].
 *
 * @throws XMLStreamException If an error occurs while reading from the XML stream.
 * @throws IllegalStateException If the document does not start with `<MDTO>` or is otherwise invalid.
 */
fun parseMdto(path: Path): MdtoContent =
    Files.newInputStream(path).use { input ->
        parseMdto(input)
    }

/**
 * Parses an InputStream containing MDTO XML data into an [MdtoContent] object.
 * This is either a [Bestand] or an [Informatieobject]
 *
 * This function reads the XML from [inputStream], and expects the first start element to be the MDTO root element (`<MDTO>`).
 *
 * @param inputStream The InputStream containing MDTO XML data
 * @return The parsed [MDTORoot].
 *
 * @throws XMLStreamException If an error occurs while reading from the XML stream.
 * @throws IllegalStateException If the document does not start with `<MDTO>` or is otherwise invalid.
 */
fun parseMdto(inputStream: InputStream): MdtoContent {
    val reader = XMLInputFactory.newInstance()
        .createXMLEventReader(inputStream)

    val mdtoStart = reader.requireNextTagAsStartElement()

    if (mdtoStart.name.localPart != MDTO_ROOT_TAG) {
        throw IllegalStateException(
            "Onverwacht root element '${mdtoStart.name.localPart}' bij regel ${mdtoStart.location}; verwacht '$MDTO_ROOT_TAG'."
        )
    }

    return getMdtoBaseClassContent(reader, mdtoStart)
}