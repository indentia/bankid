package indentia.bankid.common.domain

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

class RpV5Request {

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
    }
}



