package nl.joozd.mdto.types

import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.DateTimeFormatter

/**
 * Representatie van een XSD union van gYear, gYearMonth of date.
 */
sealed interface XsdDateUnion {
    data class YearOnly(val value: Year) : XsdDateUnion
    data class YearMonthOnly(val value: YearMonth) : XsdDateUnion
    data class FullDate(val value: LocalDate) : XsdDateUnion

    companion object{
        internal fun fromString(str: String): XsdDateUnion =
            parseXsdDateUnion(str)

        private val timezoneRegex = Regex("(Z|[+-]\\d{2}:\\d{2})$")

        private fun parseXsdDateUnion(text: String): XsdDateUnion {
            val withoutTz = text.replace(timezoneRegex, "")
            return runCatching { Year.parse(withoutTz) }
                .map { YearOnly(it) }
                .recoverCatching {
                    YearMonth.parse(withoutTz).let { YearMonthOnly(it) }
                }
                .recoverCatching {
                    LocalDate.parse(withoutTz).let { FullDate(it) }
                }
                .getOrElse {
                    throw IllegalArgumentException("Ongeldige XSD date union waarde: $text")
                }
        }
    }
}

fun XsdDateUnion.toXmlString(): String =
    when (this) {
        is XsdDateUnion.YearOnly ->
            value.toString()                    // 2024

        is XsdDateUnion.YearMonthOnly ->
            value.toString()                    // 2024-03

        is XsdDateUnion.FullDate ->
            value.format(DateTimeFormatter.ISO_LOCAL_DATE) // 2024-03-12
    }