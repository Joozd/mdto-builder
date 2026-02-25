package nl.joozd.mdto.objects

import java.net.URI

/**
 * Een geordende verzameling van gegevens in elektronische vorm,
 * die door een elektronisch apparaat onder één naam kan worden behandeld en aangesproken.
 *
 * @property omvang Aantal bytes in het bestand.
 * @property bestandsformaat De manier waarop de informatie in een computerbestand binair gecodeerd is.
 * @property checksum Gegevens om te bepalen of het bestand beschadigd of gewijzigd is.
 * @property urlBestand Actuele verwijzing naar het bestand.
 * @property isRepresentatieVan Verwijzing naar het informatieobject waarvan het bestand een (deel van een)
 *  representatie is.
 */
data class Bestand(
    override val identificatie: List<IdentificatieGegevens>,
    override val naam: String,
    val omvang: Long,
    val bestandsformaat: BegripGegevens,
    val checksum: List<ChecksumGegevens>,
    val urlBestand: URI? = null,
    val isRepresentatieVan: VerwijzingGegevens
) : MdtoBaseClassContent {

    init {
        require(identificatie.isNotEmpty()) {
            "Bestand moet minimaal één identificatie bevatten. Naam is $naam"
        }
        require(checksum.isNotEmpty()) {
            "Bestand moet minimaal één checksum bevatten."
        }
    }
}