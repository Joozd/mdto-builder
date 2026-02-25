package nl.joozd.sources

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader

/**
 * NOTE Constructor does IO
 */
class PresetValues(resourcesFileName: String) {
    private val map: Map<String, String> = this::class.java.classLoader.getResourceAsStream(resourcesFileName)?.let {
        csvReader{
            delimiter = ';'
        }.open(it) {
            readAllWithHeaderAsSequence().first() // first row as map
        }
    } ?: error ("No resource file found at $resourcesFileName")

    operator fun get(key: String): String? = map[key]

    companion object {
        const val IDENTIFICATIE_BRON = "identificatieBron"
        const val AGGREGATIENIVEAU_LABEL = "aggregatieniveau_label"
        const val AGGREGATIENIVEAU_VERWIJZING_NAAM = "aggregatieniveau_verwijzingNaam"
        const val RAADPLEEGLOCATIE_FYSIEK = "raadpleeglocatieFysiek"
        const val TAAL = "taal"
        const val VERANTWOORDELIJKE_ACTOR_CREATIE = "verantwoordelijke_actor_creatie"
        const val VERANTWOORDELIJKE_ACTOR_BEVRIEZING = "verantwoordelijke_actor_bevriezing"
        const val ARCHIEFVORMER_NAAM = "archiefvormer_naam"
        const val ARCHIEFVORMER_KENMERK = "archiefvormer_kenmerk"
        const val ARCHIEFVORMER_BRON = "archiefvormer_bron"
    }
}