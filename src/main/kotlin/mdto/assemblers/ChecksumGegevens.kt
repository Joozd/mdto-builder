package nl.joozd.mdto.assemblers

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.ChecksumGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.mdto.types.XsdDateTimeUnion
import nl.joozd.sources.Sources
import nl.joozd.utils.ErrorLogger
import java.time.OffsetDateTime

fun sha256Checksum(sources: Sources, errorLogger: ErrorLogger): ChecksumGegevens{
    val algoritmeLabel = "SHA256"
    val verwijzingNaam = "Begrippenlijst ChecksumAlgoritme MDTO"
    val checksum = sources.fileIdentification.checksumSHA256 ?: errorLogger.add("sha256Checksum", "Kan geen checksum berekenen voor ${sources.fileIdentification.naam}. Waarschijnlijk is dit een directory.")

    val algoritme = BegripGegevens(begripLabel = algoritmeLabel, begripBegrippenlijst = VerwijzingGegevens(verwijzingNaam))
    val dateTime = XsdDateTimeUnion.DateTimeOffset(OffsetDateTime.now())

    return ChecksumGegevens(algoritme, checksum, dateTime)
}



