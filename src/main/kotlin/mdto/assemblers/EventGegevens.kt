package nl.joozd.mdto.assemblers

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.EventGegevens
import nl.joozd.mdto.objects.IdentificatieGegevens
import nl.joozd.mdto.objects.VerwijzingGegevens
import nl.joozd.mdto.types.XsdDateOrDateTimeUnion
import nl.joozd.sources.Sources
import nl.joozd.utils.ErrorLogger
import java.time.OffsetDateTime

private const val DIGITALISERING = "Digitalisering"
private const val UITPLAATSING = "Uitplaatsing"
private const val BEGRIPPENLIJST = "Begrippenlijst EventTypeLijst MDTO"

fun assembleDigitaliseringEventGegevens(sources: Sources, errorLogger: ErrorLogger): EventGegevens{
    val moment = sources.fileIdentification.lastModified
    val begrip = BegripGegevens(DIGITALISERING, begripBegrippenlijst = VerwijzingGegevens(BEGRIPPENLIJST))
    val resultaat = "Bestanden gedigitaliseerd"

    return EventGegevens(eventType = begrip, eventTijd = XsdDateOrDateTimeUnion.DateTime(moment))
}

fun assembleImportEventGegevens(sources: Sources, errorLogger: ErrorLogger): EventGegevens{
    val moment = OffsetDateTime.now()
    val begrip = BegripGegevens(UITPLAATSING, begripBegrippenlijst = VerwijzingGegevens(BEGRIPPENLIJST))
    val actor = VerwijzingGegevens("Marije Welle", verwijzingIdentificatie = IdentificatieGegevens("1234567", "Gemeente Den Haag Medewerkersnummers (fictief)"))
    val resultaat = "Bestanden overgezet naar testomgeving. Niet voor permanente bewaring!"

    return EventGegevens(
        eventType = begrip,
        eventTijd = XsdDateOrDateTimeUnion.DateTime(moment),
        eventVerantwoordelijkeActor = actor,
        eventResultaat = resultaat
    )
}