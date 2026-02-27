package nl.joozd.exceptions

import javax.management.modelmbean.XMLParseException

class NotAnEndElementException(msg: String) : XMLParseException(msg)