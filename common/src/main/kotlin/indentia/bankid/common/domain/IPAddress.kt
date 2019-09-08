package indentia.bankid.common.domain

import com.fasterxml.jackson.annotation.JsonCreator
import com.google.common.net.InetAddresses.isInetAddress
import indentia.bankid.common.validated_value.ValidatedValue
import org.apache.commons.lang3.RandomUtils

class IPAddress(value: String) : ValidatedValue<String>(value, { v -> isInetAddress(v) }) {
    companion object {
        @JvmStatic
        @JsonCreator
        fun fromString(value: String): IPAddress {
            return IPAddress(value)
        }

        fun localhostV4(): IPAddress {
            return IPAddress("127.0.0.1")
        }

        fun localhostV6(): IPAddress {
            return IPAddress("::1")
        }

        fun random(): IPAddress {
            return arrayOf(localhostV4(), localhostV6())[RandomUtils.nextInt(0, 2)]
        }
    }
}
