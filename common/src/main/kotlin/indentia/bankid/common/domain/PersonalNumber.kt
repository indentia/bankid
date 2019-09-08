package indentia.bankid.common.domain

import com.fasterxml.jackson.annotation.JsonCreator
import indentia.bankid.common.validated_value.RegexValidatedValue
import indentia.bankid.common.validated_value.ValidationException
import org.apache.commons.lang3.RandomUtils
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit
import java.lang.String.format
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class PersonalNumber(value: String) : RegexValidatedValue(REGEX, value) {
    private val birthDate: LocalDate

    init {
        birthDate = birthDate()
    }

    override fun toString(): String {
        return value.substring(0, 8) + "****"
    }

    private fun birthDate(): LocalDate {
        checkValue()
        try {
            return LocalDate.of(value.substring(0, 4).toInt(), value.substring(4, 6).toInt(), value.substring(6, 8).toInt())
        } catch (e: Exception) {
            throw notValid()
        }
    }

    private fun checkValue() {
        if (!isValidLuhn(value.substring(2, 12)) || !checkBirthNumber(value.substring(8, 11))) {
            throw notValid()
        }
    }

    private fun notValid(): ValidationException {
        throw ValidationException(format(NOT_A_VALID_SWEDISH_PERSONAL_NUMBER, value))
    }

    companion object {
        val REGEX = Regex("[0-9]{12}")
        private const val NOT_A_VALID_SWEDISH_PERSONAL_NUMBER = "'%s' is not a valid (Swedish) personal number."

        @JvmStatic
        @JsonCreator
        fun fromString(value: String): PersonalNumber {
            return PersonalNumber(value)
        }

        private fun isValidLuhn(number: String): Boolean {
            return LuhnCheckDigit().isValid(number)
        }

        private fun calculateLuhn(number: String): String {
            return LuhnCheckDigit().calculate(number)
        }

        private fun checkBirthNumber(substring: String): Boolean {
            return Integer.parseInt(substring) in 1..999
        }

        fun random(): PersonalNumber {
            val minDay = LocalDate.of(1970, 1, 1).toEpochDay()
            val maxDay = LocalDate.now().toEpochDay()
            val randomDay = RandomUtils.nextLong(minDay, maxDay)
            val randomDate = LocalDate.ofEpochDay(randomDay)

            val value = randomDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + String.format("%03d", RandomUtils.nextInt(1, 1000))
            return PersonalNumber(value + calculateLuhn(value.substring(2, 11)))
        }
    }
}
