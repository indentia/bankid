package indentia.bankid.common.domain

import indentia.bankid.common.validated_value.RegexValidatedValue
import org.apache.commons.lang3.RandomUtils

class CertificatePolicy(value: String) : RegexValidatedValue(REGEX, value) {
    companion object {
        val validOids = arrayOf(
                "1\\.2\\.752\\.78\\.1\\.1",
                "1\\.2\\.752\\.78\\.1\\.2",
                "1\\.2\\.752\\.78\\.1\\.5",
                "1\\.2\\.752\\.71\\.1\\.3",
                "1\\.2\\.3\\.4\\.5",
                "1\\.2\\.3\\.4\\.10",
                "1\\.2\\.3\\.4\\.25",
                "1\\.2\\.752\\.60\\.1\\.6",
                "1\\.2\\.752\\.71\\.1\\.\\*",
                "1\\.2\\.752\\.78\\.1\\.\\*",
                "1\\.2\\.752\\.60\\.1\\.\\*"
        )

        fun random(): CertificatePolicy {
            return CertificatePolicy(validOids[RandomUtils.nextInt(0, validOids.size)].replace("\\", ""))
        }

        val REGEX = Regex("(" + validOids.joinToString(separator = "|") + ")")
    }
}
