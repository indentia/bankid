package indentia.bankid.mock.infrastructure.rest

import indentia.bankid.common.domain.IPAddress
import indentia.bankid.common.domain.RpV5Request.AuthRequest
import indentia.bankid.common.domain.RpV5Response
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@RestController()
@RequestMapping(path = ["/rp/v5"])
class MockController {

    val authControl = ConcurrentHashMap<IPAddress, AuthControlRequest>()

    @GetMapping(path = ["/ping"])
    fun ping(): String {
        return "pong"
    }

    @PostMapping(path = ["/auth"], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun auth(@RequestBody request: AuthRequest): RpV5Response.AuthResponse {
        return createAuthResponse(request)
    }

    @PostMapping(path = ["/auth/control"], consumes = [APPLICATION_JSON_VALUE])
    fun authControl(@RequestBody request: AuthControlRequest) {
        authControl[request.endUserIp] = request
    }

    fun createAuthResponse(request: AuthRequest): RpV5Response.AuthResponse {
        val controlRequest = authControl[request.endUserIp]
        return if (controlRequest != null) responseFromControlRequest(controlRequest) else MockHelper.randomAuthResponse()

    }

    fun responseFromControlRequest(control: AuthControlRequest): RpV5Response.AuthResponse {
        return control.authResponse ?: responseFromControl2(control)
    }

    private fun responseFromControl2(control: AuthControlRequest): RpV5Response.AuthResponse {
        return MockHelper.randomAuthResponse()
    }

    data class AuthControlRequest(val endUserIp: IPAddress,
                                  val authResponse: RpV5Response.AuthResponse? = null,
                                  val httpStatus: Int? = null,
                                  val errorCode: String? = null,
                                  val details: String? = null)

}