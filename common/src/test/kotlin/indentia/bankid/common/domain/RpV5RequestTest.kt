package indentia.bankid.common.domain

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.testng.annotations.Test

class RpV5RequestTest {
    @Test
    fun testFromJson() {
        val json = """{"endUserIp":"1.1.1.1", "personalNumber": "${PersonalNumber.random().value}"}"""
        assertThat(jacksonObjectMapper().readValue(json, RpV5Request.AuthRequest::class.java)).isNotNull
    }
}