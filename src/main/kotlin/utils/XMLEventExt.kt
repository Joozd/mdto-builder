package nl.joozd.utils

import javax.xml.namespace.QName
import javax.xml.stream.events.XMLEvent

internal fun XMLEvent.isEndEventFor(startEventName: QName): Boolean =
    this.isEndElement && this.asEndElement().name == startEventName