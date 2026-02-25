package nl.joozd.utils

import kotlin.time.Clock
import kotlin.time.Instant

class ErrorLogger {
    private val errors: MutableList<Error> = mutableListOf()

    /**
     * Gives an "ERROR" fallback value (assumes a string is needed, provide own fallback if something else is needed)
     */
    fun add(source: String, error: String): String{
        errors.add(Error(Clock.System.now(), source, error))
        return "ERROR"
    }

    val hasErrors get() = errors.isNotEmpty()

    fun dump(): String = errors.joinToString(separator = "\n") { it.normalize().toString() }

    data class Error(val timeStamp: Instant, val source: String, val error: String, ){
        fun normalize(): Error{
            val normalizedSource = source.take(20).padStart(20, ' ')
            return copy(source = normalizedSource)
        }

        override fun toString(): String =
            "$timeStamp | $source : $error"
    }
}