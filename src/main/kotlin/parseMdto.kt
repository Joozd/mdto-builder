package nl.joozd

import nl.joozd.mdto.objects.MDTORoot
import nl.joozd.parsing.getMdtoBaseClassContent
import nl.joozd.utils.requireNextTagAsStartElement
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamException

private const val MDTO_ROOT_TAG = "MDTO"

/**
 * Parses an MDTO XML file into an [MDTORoot].
 *
 * This function reads the XML from [path] using StAX ([XMLEventReader]) and expects the first
 * start element to be the MDTO root element (`<MDTO>`). The root element is then parsed as an
 * `mdtoType` choice (`<informatieobject>` or `<bestand>`), which becomes the [MDTORoot.content].
 *
 * @param path The path to the MDTO XML file.
 * @return The parsed [MDTORoot].
 *
 * @throws XMLStreamException If an error occurs while reading from the XML stream.
 * @throws IllegalStateException If the document does not start with `<MDTO>` or is otherwise invalid.
 */
fun parseMdto(path: Path): MDTORoot {
    Files.newInputStream(path).use { input ->
        val reader = XMLInputFactory.newInstance()
            .createXMLEventReader(input)

        val mdtoStart = reader.requireNextTagAsStartElement()

        if (mdtoStart.name.localPart != MDTO_ROOT_TAG) {
            throw IllegalStateException(
                "Onverwacht root element '${mdtoStart.name.localPart}' bij regel ${mdtoStart.location}; verwacht '$MDTO_ROOT_TAG'."
            )
        }

        val content = getMdtoBaseClassContent(reader, mdtoStart)

        return MDTORoot(content)
    }
}