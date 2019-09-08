package indentia.bankid.common.domain

import indentia.bankid.common.validated_value.ValidatedValue
import java.util.*

class Base64String(value: String) : ValidatedValue<String>(value, { v -> isValidBase64(v) }) {
    companion object {
        fun isValidBase64(value: String): Boolean {
            return try {
                Base64.getDecoder().decode(value)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}
