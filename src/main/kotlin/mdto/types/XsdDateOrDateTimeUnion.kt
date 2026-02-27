package nl.joozd.mdto.types

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter

sealed interface XsdDateOrDateTimeUnion {

    data class YearOnly(val value: Year) : XsdDateOrDateTimeUnion
    data class YearMonthOnly(val value: YearMonth) : XsdDateOrDateTimeUnion
    data class FullDate(val value: LocalDate) : XsdDateOrDateTimeUnion
    data class DateTime(val value: OffsetDateTime) : XsdDateOrDateTimeUnion

    companion object {

        private val timezoneRegex = Regex("(Z|[+-]\\d{2}:\\d{2})$")

        internal fun fromString(text: String): XsdDateOrDateTimeUnion {

            // 1. dateTime (heeft altijd 'T')
            if (text.contains('T')) {
                return runCatching { OffsetDateTime.parse(text) }
                    .map{ DateTime(it) }
                    .getOrElse {
                        throw IllegalArgumentException(
                            "Ongeldige xsd:dateTime waarde: $text"
                        )
                    }
            }

            // Voor gYear / gYearMonth / date mag timezone aanwezig zijn
            val normalized = text.replace(timezoneRegex, "")

            return runCatching { LocalDate.parse(normalized) }
                .map { FullDate(it) }
                .recoverCatching {
                    YearMonthOnly(YearMonth.parse(normalized))
                }
                .recoverCatching {
                    YearOnly(Year.parse(normalized))
                }
                .getOrElse {
                    throw IllegalArgumentException(
                        "Ongeldige XSD date/dateTime union waarde: $text"
                    )
                }
        }
    }
}

fun XsdDateOrDateTimeUnion.toXmlString(): String =
    when (this) {
        is XsdDateOrDateTimeUnion.YearOnly ->
            value.toString()

        is XsdDateOrDateTimeUnion.YearMonthOnly ->
            value.toString()

        is XsdDateOrDateTimeUnion.FullDate ->
            value.format(DateTimeFormatter.ISO_LOCAL_DATE)

        is XsdDateOrDateTimeUnion.DateTime ->
            value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }