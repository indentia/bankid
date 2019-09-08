package indentia.bankid.common.domain

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

class RpV5Response {
    data class AuthResponse(val orderRef: UUID, val autoStartToken: UUID)
    data class SignResponse(val orderRef: UUID, val autoStartToken: UUID)

    data class CollectResponse(val orderRef: UUID, val status: CollectStatus, val hintCode: String? = null, val completionData: CompletionData? = null){
        data class CompletionData(val user: User, val device: Device, val cert: Cert, val signature: String, val ocspResponse: String)
        data class User(val personalNumber: PersonalNumber, val name: String, val givenName: String, val surname: String)
        data class Device(val ipAddress: IPAddress)
        data class Cert(val notBefore: Long, val notAfter: Long)
        enum class CollectStatus(value: String) {
            PENDING("pending"),
            FAILED("failed"),
            COMPLETE("complete");
            companion object {
                @JvmStatic
                @JsonCreator
                fun fromString(value: String): CollectStatus {
                    return CollectStatus.valueOf(value);
                }
            }
        }
    }

    data class CancelResponse(val dummy: String)
}
