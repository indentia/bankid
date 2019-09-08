package indentia.bankid.common.validated_value

import org.testng.annotations.Test

class RegexValidatedValueTest {

    @Test
    fun testSimple() {
        val value = RegexValidatedValue(Regex("apa"), "apa")
    }
}