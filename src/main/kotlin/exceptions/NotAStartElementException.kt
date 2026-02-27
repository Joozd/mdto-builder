package nl.joozd.exceptions

import javax.management.modelmbean.XMLParseException

class NotAStartElementException(msg: String) : XMLParseException(msg)