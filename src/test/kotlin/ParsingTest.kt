import nl.joozd.mdto.extensions.xmlString
import nl.joozd.parseMdto
import org.junit.jupiter.api.Test
import java.nio.file.Paths
import kotlin.io.path.readText
import kotlin.test.assertEquals

class ParsingTest {
    private val testBestand = Paths.get(this::class.java.getResource("test_bestand.xml")?.toURI() ?: error ("Could not get test_bestand.xml from resources"))
    private val testInformatieobject = Paths.get(this::class.java.getResource("test_informatieobject.xml")?.toURI() ?: error ("Could not get test_informatieobject.xml from resources"))

    @Test
    fun `test parsing XML Bestand to model and then model to XML ends up the same`(){
        val xmlString = testBestand.readText()
        val textStringAsInputStream = xmlString.byteInputStream()
        val parsed = parseMdto(inputStream = textStringAsInputStream).xmlString()
        assertEquals(xmlString.stripIndents(), parsed.stripIndents())
    }

    @Test
    fun `test parsing XML Informatieobject to model and then model to XML ends up the same`(){
        val xmlString = testInformatieobject.readText()
        val textStringAsInputStream = xmlString.byteInputStream()
        val parsed = parseMdto(inputStream = textStringAsInputStream).xmlString()
        assertEquals(xmlString.stripIndents(), parsed.stripIndents())
    }
}

/**
 * Gets rid of newline and indentation inequalities
 */
private fun String.stripIndents() =
    lines().joinToString("\n") { it.trim()}
