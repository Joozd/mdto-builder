package nl.joozd.parsing

import nl.joozd.mdto.objects.IdentificatieGegevens
import nl.joozd.utils.isEndEventFor
import nl.joozd.utils.readSimpleElementText
import nl.joozd.utils.requireNextTagAsStartElement
import javax.xml.namespace.QName
import javax.xml.stream.XMLEventReader

private const val IDENTIFICATIE_TAG = "identificatie"
private const val NAAM_TAG = "naam"

internal fun readObjectTypeBase(
    reader: XMLEventReader,
    startEventName: QName,
): ObjectTypeBase {

    val identificaties = mutableListOf<IdentificatieGegevens>()
    var naam: String? = null

    var currentEvent = reader.requireNextTagAsStartElement()

    while (!currentEvent.isEndEventFor(startEventName)) {
        when (currentEvent.name.localPart) {
            IDENTIFICATIE_TAG ->
                identificaties += getIdentificatieGegevens(reader, currentEvent)

            NAAM_TAG ->
                naam = reader.readSimpleElementText(currentEvent)
        }

        currentEvent = reader.requireNextTagAsStartElement()
    }

    if (identificaties.isEmpty() || naam == null) {
        throw IllegalStateException("objectType incompleet bij regel ${currentEvent.location}")
    }

    return ObjectTypeBase(identificaties, naam)
}