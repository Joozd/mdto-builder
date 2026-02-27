package nl.joozd

import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.XMLConstants
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

private const val BUNDLED_XSD_FILE = "MDTO-XML1.0.1.xsd"

/**
 * Validates the given XML file against the MDTO 1.0.1 XSD located on the classpath (resources).
 * This is the version that is modeled in this library.
 *
 * @param xmlPath Path to the XML file to validate.
 * @param xsdUrl URL of XSD Resource.
 *
 * @throws IllegalStateException If the XSD resource cannot be found.
 * @throws org.xml.sax.SAXParseException If validation fails (includes line/column).
 */
fun validateMdtoXml(
    xmlPath: Path,
    xsdUrl: URL = getBundledXSDURL(),
) {
    val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).apply {
        setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)
        setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "")
        setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "")
    }

    val schema = schemaFactory.newSchema(xsdUrl)
    val validator = schema.newValidator()

    Files.newInputStream(xmlPath).use { xmlIn ->
        validator.validate(StreamSource(xmlIn))
    }
}

private fun getBundledXSDURL(
    classLoader: ClassLoader = Thread.currentThread().contextClassLoader
): URL =
    requireNotNull(classLoader.getResource(BUNDLED_XSD_FILE)) {
        "XSD resource not found on classpath: $BUNDLED_XSD_FILE"
    }

