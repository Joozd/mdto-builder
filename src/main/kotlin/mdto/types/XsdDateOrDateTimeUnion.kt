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