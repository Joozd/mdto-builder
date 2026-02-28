package nl.joozd.mdto.types

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

sealed interface XsdDateTimeUnion {
    data class DateTimeOffset(val dateTime: OffsetDateTime) : XsdDateTimeUnion
    data class DateTime(val dateTime: LocalDateTime) : XsdDateTimeUnion

    companion object{
        internal fun fromString(str: String): XsdDateTimeUnion =
            parseXsdDateTimeUnion(str)

        private fun parseXsdDateTimeUnion(text: String): XsdDateTimeUnion {
            return runCatching { DateTimeOffset(OffsetDateTime.parse(text)) }
                .recoverCatching { DateTime(LocalDateTime.parse(text)) }
                .getOrElse { e ->
                    throw IllegalArgumentException("cannot parse $text into an XsdDateTimeUnion", e)
                }
        }
    }
}

fun XsdDateTimeUnion.toXmlString(): String =
    when (this) {
        is XsdDateTimeUnion.DateTimeOffset ->
            dateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        is XsdDateTimeUnion.DateTime ->
            dateTime.format(DateTimeFormatter.ISO_DATE_TIME)
    }