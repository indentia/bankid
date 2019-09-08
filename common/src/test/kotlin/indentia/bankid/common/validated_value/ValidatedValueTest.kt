package indentia.bankid.common.validated_value

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.testng.annotations.Test

class ValidatedValueTest {

    class ValidatedValueForTest(value: String, isValid: Boolean) : ValidatedValue<String>(value, { isValid })

    @Test
    fun testValid() {
        assertThat(ValidatedValueForTest("", true)).isNotNull
    }

    @Test
    fun testInvalid() {
        assertThatThrownBy { ValidatedValueForTest("apa", false) }
                .isExactlyInstanceOf(ValidationException::class.java)
                .hasMessage("'apa' is not a valid ${ValidatedValueForTest::class.java.name}")
    }

    @Test
    fun testInvalidNonPrintable() {
        assertThatThrownBy { ValidatedValueForTest(0.toChar().toString(), false) }
                .isExactlyInstanceOf(ValidationException::class.java)
                .hasMessage("'\\u0000' is not a valid ${ValidatedValueForTest::class.java.name}")
    }

    @Test
    fun testInvalidTruncate() {
        assertThatThrownBy { ValidatedValueForTest("0123456789012345678901234567890123456789012345678901234567890123456789", false) }
                .isExactlyInstanceOf(ValidationException::class.java)
                .hasMessage("'0123456789012345678901234567890123456789012345678901234567890123' is not a valid ${ValidatedValueForTest::class.java.name}")
    }
}