package indentia.bankid.mock.infrastructure.rest

import com.fasterxml.jackson.databind.ObjectMapper
import indentia.bankid.mock.infrastructure.rest.MockHelper.Companion.randomAuthRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test

@EnableWebMvc
@SpringBootTest(classes = [MockControllerTest.Conf::class], webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class MockControllerTest : AbstractTestNGSpringContextTests() {

    @Import(MockController::class)
    @Configuration
    class Conf {
    }

    @Autowired
    lateinit var wac: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @BeforeMethod
    fun prepareMockMvc() {
        mockMvc = webAppContextSetup(wac).build()
    }

    @Test
    fun testPing() {
        mockMvc.perform(get("/rp/v5/ping"))
                .andExpect(status().isOk)
                .andExpect(MockMvcResultMatchers.content().string("pong"))
    }

    @Test
    fun testAuth() {
        val json = json(randomAuthRequest())
        val result = mockMvc.perform(post("/rp/v5/auth")
                .contentType(APPLICATION_JSON).content(json))
                .andExpect(status().isOk)
    }

    private fun json(value: Any) = ObjectMapper().writeValueAsString(value)
}