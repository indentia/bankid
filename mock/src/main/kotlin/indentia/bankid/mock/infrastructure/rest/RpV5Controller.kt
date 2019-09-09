package indentia.bankid.mock.infrastructure.rest

import indentia.bankid.common.domain.IPAddress
import indentia.bankid.common.domain.RpV5Request
import indentia.bankid.common.domain.RpV5Response
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.HttpServletResponse.SC_OK

@RestController()
@RequestMapping(path = ["/rp/v5"])
class RpV5Controller {

    val authControl = ConcurrentHashMap<IPAddress, AuthControlRequest>()

    @GetMapping(path = ["/ping"])
    fun ping(): String {
        return "pong"
    }

    @PostMapping(path = ["/auth"], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun auth(@RequestBody request: RpV5Request.Auth): RpV5Response.Auth {
        return createAuthResponse(request)
    }

    @PostMapping(path = ["/auth/control"], consumes = [APPLICATION_JSON_VALUE])
    fun authControl(@RequestBody request: AuthControlRequest) {
        authControl[request.endUserIp] = request
    }

    fun createAuthResponse(request: RpV5Request.Auth): RpV5Response.Auth {
        val authControlRequest = authControl[request.endUserIp]

        val httpStatus = authControlRequest?.httpStatus ?: SC_OK
        val mockedResponse = authControlRequest?.mockedResponse
        val responseBuilder = ResponseEntity.status(httpStatus)

        return mockedResponse ?: randomOrErrorResponse(httpStatus, authControlRequest?.mockedErrorResponse)
    }

    private fun randomOrErrorResponse(builder: ResponseEntity.BodyBuilder, mockedErrorResponse: ErrorController.ErrorResponse?): RpV5Response.Auth {
        return builder.body(mockedErrorResponse ?: randomAuthResponse())
    }

    private fun randomAuthResponse(): ResponseEntity<Any> = ResponseEntity.status(SC_OK).body(MockHelper.randomAuthResponse())

    data class AuthControlRequest(val endUserIp: IPAddress,
                                  val mockedResponse: RpV5Response.Auth? = null,
                                  val httpStatus: Int?,
                                  val mockedErrorResponse: ErrorController.ErrorResponse?)
}