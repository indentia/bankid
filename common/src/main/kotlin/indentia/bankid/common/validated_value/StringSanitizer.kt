package indentia.bankid.common.validated_value

import kotlin.math.abs

object StringSanitizer {

    fun sanitize(value: String, max: Int): String {
        return (0 until minOf(value.length, abs(max))).joinToString(separator = "") {
            val c: Int = value[it].toInt()
            if (c !in 32..126) toHex(c) else toString(c)
        }
    }

    private fun toString(c: Int) = c.toChar().toString()

    private fun toHex(c: Int) = "\\u" + padHex(c)

    private fun padHex(c: Int) = c.toString(16).padStart(4, '0').toUpperCase()
}
