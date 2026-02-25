package nl.joozd

import com.sun.xml.txw2.output.IndentingXMLStreamWriter
import nl.joozd.mdto.assemblers.assembleBestand
import nl.joozd.mdto.assemblers.assembleInformatieObject
import nl.joozd.mdto.objects.MDTORoot
import nl.joozd.mdto.xmlwriters.emit
import nl.joozd.sources.FileIdentification
import nl.joozd.sources.PresetValues
import nl.joozd.sources.Sources
import nl.joozd.utils.ErrorLogger
import java.nio.file.Files
import javax.xml.stream.XMLOutputFactory
import kotlin.io.path.Path
import kotlin.io.path.createFile

private val ROOT = Path("C:\\xfer\\Preservica Testset")
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val presetValues = PresetValues("mdto_defaults.csv")
    val errorLogger = ErrorLogger()
    var counter = 0

    val xmlFactory = XMLOutputFactory.newFactory()

    Files.walk(ROOT)
        .skip(1) // skip root zelf
        .use { stream ->

        stream.forEach { path ->
            counter++
            if(counter%25 == 0) print('.')
            val sources = Sources(presetValues, FileIdentification(path))
            val content =
                if (Files.isDirectory(path))
                    assembleInformatieObject(sources, errorLogger)
                else
                    assembleBestand(sources, errorLogger)

            val mdtoRoot = MDTORoot(content)
            if(errorLogger.hasErrors) {
                println("\n${errorLogger.dump()}")
                throw IllegalStateException("Errors found. Exiting.")
            }

            val newFileName = "$path.mdto.xml"
            val newFile = Path(newFileName).apply{ createFile() }
            try {
                Files.newOutputStream(newFile).use { out ->
                    val base = xmlFactory.createXMLStreamWriter(out, "UTF-8")
                    val writer = IndentingXMLStreamWriter(base)
                    mdtoRoot.emit(writer)
                    writer.flush()
                    writer.close()
                }
            }catch (e: Throwable) {
                println("error in $path")
                throw e
            }
        }
    }
}
