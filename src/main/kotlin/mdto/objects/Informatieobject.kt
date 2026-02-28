package nl.joozd.mdto.objects

/**
 * MDTO Informatieobject (informatieobjectType).
 *
 * @property aggregatieniveau Het aggregatieniveau van het informatieobject.
 * @property classificatie Ordening van informatieobjecten in een logisch verband.
 * @property trefwoord Trefwoorden toegekend aan het informatieobject.
 * @property omschrijving Omschrijving van de inhoud van het informatieobject.
 * @property raadpleeglocatie Actuele verwijzing naar inzagelocatie.
 * @property dekkingInTijd Tijdstip of periode waarop de inhoud betrekking heeft.
 * @property dekkingInRuimte Plaats of locatie waarop de inhoud betrekking heeft.
 * @property taal Taal waarin het informatieobject is gesteld (ISO language code).
 * @property event Gebeurtenissen m.b.t. ontstaan, wijziging of beheer.
 * @property waardering Waardering volgens vastgestelde selectielijst.
 * @property bewaartermijn Bewaartermijn volgens selectielijst.
 * @property informatiecategorie Informatiecategorie waarop bewaartermijn is gebaseerd.
 * @property isOnderdeelVan Bovenliggende aggregatie.
 * @property bevatOnderdeel Direct onderliggende informatieobjecten.
 * @property heeftRepresentatie Verwijzing naar representatiebestand.
 * @property aanvullendeMetagegevens Verwijzing naar aanvullende metadata.
 * @property gerelateerdInformatieobject Relatie met ander informatieobject.
 * @property archiefvormer Organisatie verantwoordelijk voor ontstaan/ontvangst.
 * @property betrokkene Betrokken persoon of organisatie.
 * @property activiteit Bedrijfsactiviteit waarbij object is ontvangen/gemaakt.
 * @property beperkingGebruik Beperkingen op gebruik van het informatieobject.
 */
data class Informatieobject(
    override val identificatie: List<IdentificatieGegevens>,
    override val naam: String,

    val aggregatieniveau: BegripGegevens? = null,
    val classificatie: List<BegripGegevens> = emptyList(),
    val trefwoord: List<String> = emptyList(),
    val omschrijving: List<String> = emptyList(),
    val raadpleeglocatie: List<RaadpleeglocatieGegevens> = emptyList(),
    val dekkingInTijd: List<DekkingInTijdGegevens> = emptyList(),
    val dekkingInRuimte: List<VerwijzingGegevens> = emptyList(),
    val taal: List<String> = emptyList(),
    val event: List<EventGegevens> = emptyList(),

    val waardering: BegripGegevens,
    val bewaartermijn: TermijnGegevens? = null,
    val informatiecategorie: BegripGegevens? = null,

    val isOnderdeelVan: List<VerwijzingGegevens> = emptyList(),
    val bevatOnderdeel: List<VerwijzingGegevens> = emptyList(),
    val heeftRepresentatie: List<VerwijzingGegevens> = emptyList(),
    val aanvullendeMetagegevens: List<VerwijzingGegevens> = emptyList(),
    val gerelateerdInformatieobject: List<GerelateerdInformatieobjectGegevens> = emptyList(),

    val archiefvormer: List<VerwijzingGegevens>,
    val betrokkene: List<BetrokkeneGegevens> = emptyList(),

    val activiteit: VerwijzingGegevens? = null,
    val beperkingGebruik: List<BeperkingGebruikGegevens>,
) : MdtoContent{
    init {
        require(identificatie.isNotEmpty()) {
            "Informatieobject moet minimaal één identificatie bevatten."
        }
        require(archiefvormer.isNotEmpty()) {
            "Informatieobject moet minimaal één archiefvormer bevatten."
        }
        require(beperkingGebruik.isNotEmpty()) {
            "Informatieobject moet minimaal één beperkingGebruik bevatten."
        }
    }
}