package indentia.bankid.common.validated_value

import java.util.*

object Validators {
    class RegexValidator(private val regex: Regex) : (String) -> Boolean {
        override fun invoke(value: String): Boolean = regex.matches(value)
    }

    class StringLengthValidator(private val minLength: Int, private val maxLength: Int) : (String) -> Boolean {
        override fun invoke(value: String): Boolean = value.length in minLength..maxLength
    }

    class UUIDValidator(value: String) : (String) -> Boolean {
        override fun invoke(value: String): Boolean = try {
            UUID.fromString(value); true
        } catch (e: Exception) {
            false
        }
    }
}