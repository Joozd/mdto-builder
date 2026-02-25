package nl.joozd.sources

import org.apache.tika.Tika
import java.nio.file.Files
import java.nio.file.Path
import java.security.MessageDigest
import java.time.OffsetDateTime
import java.time.ZoneId
import kotlin.io.path.isDirectory

class FileIdentification(file: Path) {
    val isDirectory = file.isDirectory()
    val naam: String = file.fileName.toString()
    override fun toString() = "FileIdentification($naam)"

    private val bestandsIdentificatieData: BestandsIdentificatieData? = FILE_REGEX.matchEntire(naam)?.let { match ->
        val toegang = match.groupValues[GROUP_TOEGANG]
        val informatieobject = match.groupValues[GROUP_INFORMATIEOBJECT]
        val volgnummer = match.groupValues[GROUP_VOLGNUMMER]
        val fileType = match.groupValues[GROUP_FILETYPE]

        BestandsIdentificatieData(toegang, informatieobject, volgnummer, fileType)
    }

    val mimeType: String? by lazy {
        if(isDirectory) null else
        Tika().detect(file)
    }

    val checksumSHA256: String? by lazy{
        if(isDirectory) null
        else sha256(file)
    }

    val fileSize: Long? = if(isDirectory) null else Files.size(file)

    /**
     * Lijst met alles waar dit een onderdeel van uitmaakt.
     * Voor een bestand is dit `isRepresentatieVan`
     */
    val isOnderdeelVan: List<String> =
        if(isDirectory)
            if (naam matches toegangRegex)
                emptyList()
            else listOfNotNull(file.parent?.fileName?.toString())
        else listOfNotNull(
            file.parent?.fileName?.toString(),
            bestandsIdentificatieData?.informatieobject
        ).distinct()

    /**
     * Lijst van alle identificatiekenmerken
     */
    val identificatieKenmerk: List<String> = if(isDirectory)
        listOf(naam)
    else bestandsIdentificatieData?.let { data ->
        listOf(
            data.volgnummer,
            data.informatieobject + "_" + data.volgnummer,
        )
    } ?: emptyList()

    val lastModified: OffsetDateTime =
        Files.getLastModifiedTime(file)
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toOffsetDateTime()


    companion object{
        // Whole string is 4 digits, hyphen, 2 digits
        private val toegangRegex = """^\d{4}-\d{2}$""".toRegex()

        //whole string is only digits
        private val dossierNummerRegex = """^\d+$""".toRegex()


        /*
         * voorbeeld van een filenaam: NL-HaHGA_0003-01_1242_0001.jpg
         * opgebouwd als:
         * NL-HaHGA_ (discard)
         * 0003-01 (toegang -> is_onderdeel_van_verwijzing_identificatie_identificatie_kenmerk)
         * _ (discard)
         * 1242 (informatieobject -> isRepresentatieVan)
         * _ (discard)
         * 0001 -> volgnummer (identificatie)
         * .jpg -> fileType (bestandsformaat)
         */
        private val FILE_REGEX  = """^NL-HaHGA_(\d{4}-\d{2})_(.+)_(\d{4})\.([a-z]{3})$""".toRegex()

        private const val GROUP_TOEGANG = 1
        private const val GROUP_INFORMATIEOBJECT = 2
        private const val GROUP_VOLGNUMMER = 3
        private const val GROUP_FILETYPE = 4
    }

    private data class BestandsIdentificatieData(
        val toegang: String,
        val informatieobject: String,
        val volgnummer: String,
        val fileType: String
    )

    private fun sha256(path: Path): String {
        val digest = MessageDigest.getInstance("SHA-256")

        Files.newInputStream(path).use { input ->
            val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } != -1) {
                digest.update(buffer, 0, bytesRead)
            }
        }

        return digest.digest()
            .joinToString("") { "%02x".format(it) }
    }
}




