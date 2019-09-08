package indentia.bankid.common.domain

import indentia.bankid.common.validated_value.ValidationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class PersonalNumberTest {

    @DataProvider(name = "validValues")
    fun validValues(): Array<Array<Any>> {
        return arrayOf<Array<Any>>(arrayOf("197110257594"), arrayOf("191212121212"))
    }

    @Test(dataProvider = "validValues")
    fun testValidValues(value: String) {
        try {
            val personalNumber = PersonalNumber(value)
            assertThat(personalNumber.toString()).endsWith("****")
        } catch (e: ValidationException) {
            assertThat(false)
        }
    }

    @DataProvider(name = "invalidValues")
    fun invalidValues(): Array<Array<Any>> {
        return arrayOf(arrayOf<Any>(""), arrayOf<Any>("51185681933"), arrayOf<Any>("9690171068270"), arrayOf<Any>("199511150006"))
    }

    @Test(dataProvider = "invalidValues")
    fun testInvalidValues(value: String) {
        assertThatThrownBy { PersonalNumber(value) }.isExactlyInstanceOf(ValidationException::class.java)
    }

    @Test
    fun testRandom() {
        for (i in 1..1000) {
            PersonalNumber.random()
        }
    }
}