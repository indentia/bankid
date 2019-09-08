package indentia.bankid.common.validated_value

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class PrintableValidatedValueTest {
    @DataProvider
    fun testValidData(): Array<Array<String>> =
            arrayOf(arrayOf("apa"),
                    arrayOf("APA"),
                    arrayOf("ApA"),
                    arrayOf("Räksmörgås"),
                    arrayOf("apa12356"),
                    arrayOf("a"),
                    arrayOf("a b"),
                    arrayOf("a b "),
                    arrayOf("!\"#$%&'()*+,-./:;<"),
                    arrayOf("=>?@[\\]^_`{|}~"),
                    arrayOf(" "),
                    arrayOf("Księżyc Milczy Luty"))

    @Test(dataProvider = "testValidData")
    fun testValidValue(value: String) {
        assertThat(PrintableValidatedValue(value, value.length).value).isEqualTo(value)
    }

    @DataProvider
    fun testInvalidData(): Array<Array<String>> =
            arrayOf(arrayOf("\tapa"), arrayOf("\napa"), arrayOf("\rapa"), arrayOf("\u0007"))

    @Test(dataProvider = "testInvalidData")
    fun testInvalid(value: String) {
        assertThatThrownBy { PrintableValidatedValue(value, value.length) }
                .isExactlyInstanceOf(ValidationException::class.java)
                .hasMessageMatching(".* is not a valid ${PrintableValidatedValue::class.java.name}")
    }
}