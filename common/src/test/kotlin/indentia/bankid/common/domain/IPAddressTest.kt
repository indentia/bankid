package indentia.bankid.common.domain

import indentia.bankid.common.validated_value.ValidationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class IPAddressTest {
    @DataProvider(name = "invalidIPAddresses")
    fun invalidIPAddresses(): Array<Array<Any>> {
        return arrayOf(
                arrayOf<Any>(""),
                arrayOf<Any>("127.0.0."),
                arrayOf<Any>("127.0.."),
                arrayOf<Any>("127..."),
                arrayOf<Any>(".0.0.0"),
                arrayOf<Any>("256.0.0.0"),
                arrayOf<Any>("255.255.0.256"),
                arrayOf<Any>("0.0.0.0."),
                arrayOf<Any>("0.0.0.0.0"),
                arrayOf<Any>("0.0.0.x"),
                arrayOf<Any>("0.0000.0.0"),
                arrayOf<Any>("http://[2001:db8:0:1]:80"),
                arrayOf<Any>("[2001:db8:0:1]:80"),
                arrayOf<Any>("[2001:db8::1]:80")

        )
    }

    @Test(dataProvider = "invalidIPAddresses")
    fun testInvalid(invalidIpAddress: String) {
        assertThatThrownBy { IPAddress(invalidIpAddress) }
                .isExactlyInstanceOf(ValidationException::class.java)
    }

    @DataProvider(name = "validIPAddresses")
    fun validIPAddresses(): Array<Array<Any>> {
        return arrayOf(
                arrayOf<Any>(IPAddress.localhostV4().value),
                arrayOf<Any>(IPAddress.localhostV6().value),
                arrayOf<Any>("0.0.0.0"),
                arrayOf<Any>("10.10.10.0"),
                arrayOf<Any>("1.0.0.0"),
                arrayOf<Any>("0.0.0.0"),
                arrayOf<Any>("255.255.255.255"),
                arrayOf<Any>("FE80:0000:0000:0000:0202:B3FF:FE1E:8329"),
                arrayOf<Any>("FE80::0202:B3FF:FE1E:8329")
        )
    }

    @Test(dataProvider = "validIPAddresses")
    fun testValidIpAddress(validIpAddress: String) {
        assertThat(IPAddress(validIpAddress).value).isEqualTo(validIpAddress)
    }
}
