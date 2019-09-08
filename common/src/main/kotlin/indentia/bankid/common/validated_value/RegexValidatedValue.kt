package indentia.bankid.common.validated_value

import indentia.bankid.common.validated_value.Validators.RegexValidator

open class RegexValidatedValue(regex: Regex, value: String) : ValidatedValue<String>(value, RegexValidator(regex))
