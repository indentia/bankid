package indentia.bankid.common.domain

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

object RpV5Response {
    data class Auth(val orderRef: UUID, val autoStartToken: UUID)
    data class Sign(val orderRef: UUID, val autoStartToken: UUID)
    data class Cancel(val dummy: String)

    data class Collect(val orderRef: UUID, val status: Status, val hintCode: String? = null, val completionData: CompletionData? = null) {
        data class CompletionData(val user: User, val device: Device, val cert: Cert, val signature: String, val ocspResponse: String)
        data class User(val personalNumber: PersonalNumber, val name: String, val givenName: String, val surname: String)
        data class Device(val ipAddress: IPAddress)
        data class Cert(val notBefore: Long, val notAfter: Long)
        enum class Status(value: String) {
            PENDING("pending"),
            FAILED("failed"),
            COMPLETE("complete");

            companion object {
                @JvmStatic
                @JsonCreator
                fun fromString(value: String): Status {
                    return Status.valueOf(value);
                }
            }
        }
    }
}
