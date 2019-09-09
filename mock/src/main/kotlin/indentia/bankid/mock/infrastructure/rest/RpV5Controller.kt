package indentia.bankid.mock.infrastructure.rest

import indentia.bankid.common.domain.IPAddress
import indentia.bankid.common.domain.RpV5Request
import indentia.bankid.common.domain.RpV5Response
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

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

        val mockedResponse = authControlRequest?.mockedResponse
        return when {
            (mockedResponse != null) -> mockedResponse
            (authControlRequest?.error != null) -> throw rpV5Exception(authControlRequest.error)
            else -> MockHelper.randomAuthResponse()
        }
    }

    private fun rpV5Exception(error: AuthControlRequest.Error): RpV5Exception {
        return RpV5Exception(error.httpStatus, error.errorCode, error.details)
    }

    data class AuthControlRequest(val endUserIp: IPAddress,
                                  val mockedResponse: RpV5Response.Auth? = null,
                                  val error: Error?) {

        data class Error(val httpStatus: Int, val errorCode: String, val details: String)
    }
}