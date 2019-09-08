package indentia.bankid.common.domain

import com.fasterxml.jackson.annotation.JsonCreator
import indentia.bankid.common.validated_value.RegexValidatedValue
import org.apache.commons.lang3.RandomUtils
import java.util.*

object RpV5Request {

    data class AuthRequest(val endUserIp: IPAddress,
                           val personalNumber: PersonalNumber? = null,
                           val Requirement: Requirement? = null)

    data class SignRequest(val endUserIp: IPAddress,
                           val personalNumber: PersonalNumber?,
                           val Requirement: Requirement?,
                           val userVisibleData: Base64String?,
                           val userNonVisibleData: Base64String?)

    data class CollectRequest(val orderRef: UUID)

    data class CancelRequest(val orderRef: UUID)

    @Suppress("ArrayInDataClass")
    data class Requirement(val cardReader: CardReaderClass?,
                           val certificatePolicies: Array<CertificatePolicy>?,
                           val issuerCn: Array<String>?,
                           val autoStartTokenRequired: Boolean?,
                           val allowFingerprint: Boolean?) {
        enum class CardReaderClass(value: String) {
            CLASS_1("class1"),
            CLASS_2("class2");

            companion object {
                @JvmStatic
                @JsonCreator
                fun fromString(value: String): CardReaderClass {
                    return CardReaderClass.valueOf(value);
                }
            }
        }

        class CertificatePolicy(value: String) : RegexValidatedValue(REGEX, value) {
            companion object {
                val ValidOids = arrayOf(
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
                    return CertificatePolicy(ValidOids[RandomUtils.nextInt(0, ValidOids.size)].replace("\\", ""))
                }

                val REGEX = Regex("(" + ValidOids.joinToString(separator = "|") + ")")
            }
        }
    }
}



