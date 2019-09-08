package indentia.bankid.common.validated_value

import indentia.bankid.common.validated_value.Validators.RegexValidator
import indentia.bankid.common.validated_value.Validators.StringLengthValidator

class PrintableValidatedValue(value: String, maxLength: Int) : ValidatedValue<String>(value, RegexValidator(REGEX), StringLengthValidator(1, maxLength)) {
    companion object {
        val REGEX = Regex("[\\p{IsLatin}\\p{Print}]+")
    }
}