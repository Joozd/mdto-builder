package nl.joozd.mdto.types

import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.Duration as XmlDuration

@JvmInline
value class XsdDuration private constructor(
    val value: XmlDuration
) {
    companion object {
        private val factory: DatatypeFactory = DatatypeFactory.newInstance()

        fun parse(lexical: String): XsdDuration =
            XsdDuration(factory.newDuration(lexical))
    }

    override fun toString(): String = value.toString()
}