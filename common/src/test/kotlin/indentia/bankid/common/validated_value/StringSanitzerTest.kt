package indentia.bankid.common.validated_value

import indentia.bankid.common.validated_value.StringSanitizer.sanitize
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class StringSanitizerTest {

    @DataProvider
    fun provider(): Array<Array<String>> {
        return arrayOf(
                arrayOf("\u001F", "\\u001F"),
                arrayOf("\u0020", "\u0020"),
                arrayOf("\u007E", "\u007E"),
                arrayOf("\u007F", "\\u007F"),
                arrayOf("\u0080", "\\u0080"))
    }

    @Test(dataProvider = "provider")
    fun shouldEscapeNonPrintableChars(input: String, expectedOutput: String) {
        assertThat(sanitize(input, 1)).isEqualTo(expectedOutput)
    }

    @Test
    fun shouldTruncateLongInputs() {
        assertThat(sanitize("abcdef", 3)).isEqualTo("abc")
    }

    @Test
    fun shouldLetShortInputsThrough() {
        assertThat(sanitize("abcdef", 9)).isEqualTo("abcdef")
    }
}