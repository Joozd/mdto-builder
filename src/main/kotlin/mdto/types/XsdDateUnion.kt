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