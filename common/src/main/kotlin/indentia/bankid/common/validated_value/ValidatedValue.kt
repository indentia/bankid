package indentia.bankid.common.validated_value

import com.fasterxml.jackson.annotation.JsonValue
import indentia.bankid.common.validated_value.Validate.validateNonNull

abstract class ValidatedValue<T>(value: T, vararg validators: (T) -> Boolean) {

    @JsonValue
    val value: T

    init {
        validateNonNull(value, "null is not a valid ${javaClass.name}")
        validators.forEach {
            if (!it(value)) {
                throw ValidationException("'${sanitize(value)}' is not a valid ${javaClass.name}")
            }
        }
        this.value = value
    }

    private fun sanitize(value: T): String = StringSanitizer.sanitize(value.toString(), 64)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ValidatedValue<*>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int = value?.hashCode() ?: 0

    override fun toString(): String = "ValidatedValue(value=$value)"
}
