package nl.joozd.parsing

import nl.joozd.mdto.objects.BegripGegevens
import nl.joozd.mdto.objects.ChecksumGegevens
import nl.joozd.mdto.types.XsdDateTimeUnion
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLStreamException
import javax.xml.stream.events.StartElement
import javax.xml.stream.events.XMLEvent

private const val CHECKSUM_ALGORITME_TAG = "checksumAlgoritme"
private const val CHECKSUM_WAARDE_TAG = "checksumWaarde"
private const val CHECKSUM_DATUM_TAG = "checksumDatum"

/**
 * Parses a `checksumGegevens` element from the given [reader].
 *
 * The reader must be positioned on the `START_ELEMENT` represented by
 * [startEvent], which is expected to correspond to an element of type
 * `checksumGegevens` as defined in the MDTO XSD.
 *
 * This function consumes all events until the matching `END_ELEMENT`
 * for [startEvent] is encountered.
 *
 * According to the schema, the expected child elements (in order) are:
 * - `checksumAlgoritme` (required, complex type `begripGegevens`)
 * - `checksumWaarde` (required, simple text)
 * - `checksumDatum` (required, simple text in `xsd:dateTime` lexical form)
 *
 * @param reader The [XMLEventReader] to read from.
 * @param startEvent The `START_ELEMENT` for the `checksumGegevens` block.
 *
 * @return A fully populated [ChecksumGegevens] instance.
 *
 * @throws XMLStreamException If an error occurs while reading from the XML stream.
 * @throws IllegalStateException If one or more required elements are missing.
 *
 * After successful return, the reader will be positioned on the matching
 * `END_ELEMENT` of [startEvent].
 */
internal fun getChecksumGegevens(
    reader: XMLEventReader,
    startEvent: StartElement,
): ChecksumGegevens {
    val startEventName = startEvent.name
    var currentEvent: XMLEvent = startEvent

    var algoritme: BegripGegevens? = null
    var waarde: String? = null
    var datum: XsdDateTimeUnion? = null

    while (reader.hasNext() && !currentEvent.isEndEventFor(startEventName)) {
        val nextEvent = reader.nextTag()
        if(nextEvent.isEndElement) {
            currentEvent = nextEvent
            continue
        }
        currentEvent = nextEvent.asStartElement()


        when (currentEvent.name.localPart) {
            CHECKSUM_ALGORITME_TAG -> algoritme = getBegripGegevens(reader, currentEvent)
            CHECKSUM_WAARDE_TAG -> waarde = reader.readSimpleElementText(currentEvent)
            CHECKSUM_DATUM_TAG -> {
                val datumText = reader.readSimpleElementText(currentEvent)
                datum = XsdDateTimeUnion.fromString(datumText)


            } // consume and discard
        }
    }

    if (algoritme == null || waarde == null || datum == null) {
        throw IllegalStateException(
            "ERROR 0005: ChecksumGegevens incompleet bij regel ${startEvent.location}"
        )
    }

    return ChecksumGegevens(
        checksumAlgoritme = algoritme,
        checksumWaarde = waarde,
        checksumDatum = datum,
    )
}