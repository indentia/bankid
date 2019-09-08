package indentia.bankid.common.validated_value

import java.lang.String.format
import java.util.Objects.isNull

object Validate {

    fun validate(condition: Boolean, messageTemplate: String, vararg messageArgs: Any) {
        if (!condition) {
            throw ValidationException(format(messageTemplate, *messageArgs))
        }
    }

    fun <T> validateNonNull(value: T, messageTemplate: String, vararg messageArgs: Any): T {
        if (isNull(value)) {
            throw ValidationException(format(messageTemplate, *messageArgs))
        }

        return value
    }

    fun validateWithRegex(value: String, regex: java.util.regex.Pattern, messageTemplate: String, vararg messageArgs: Any): String {
        if (!regex.matcher(value).matches()) {
            throw ValidationException(format(messageTemplate, *messageArgs))
        }

        return value
    }

    fun <T> isValid(consumerThatCanThrowValidationException: java.util.function.Consumer<T>, value: T): Boolean {
        try {
            consumerThatCanThrowValidationException.accept(value)
            return true
        } catch (e: ValidationException) {
            return false
        }

    }
}