package nl.joozd.mdto.xmlwriters


/**
 * XML-gerelateerde constanten voor MDTO serialisatie.
 *
 * Bevat namespaces, prefixes en schema-locatie informatie.
 * Dit object hoort uitsluitend in de XML-laag en niet in het domain.
 */
internal object MDTOXmlConstants {

    /** Default namespace voor MDTO XML. */
    const val NS = "https://www.nationaalarchief.nl/mdto"

    /** Prefix voor XML Schema Instance namespace. */
    const val XSI_PREFIX = "xsi"

    /** XML Schema Instance namespace URI. */
    const val XSI_NS = "http://www.w3.org/2001/XMLSchema-instance"

    /** Locatie van het MDTO XSD bestand. */
    const val SCHEMA_LOCATION =
        "https://www.nationaalarchief.nl/mdto https://www.nationaalarchief.nl/mdto/MDTO-XML1.0.1.xsd"

    /** Root element naam. */
    const val ROOT_ELEMENT = "MDTO"
}