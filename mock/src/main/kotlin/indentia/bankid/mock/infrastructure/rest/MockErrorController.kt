package indentia.bankid.mock.infrastructure.rest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.ErrorProperties
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

@Controller
@RequestMapping("\${server.error.path:\${error.path:/error}}")
class MockErrorController @Autowired constructor(errorAttributes: ErrorAttributes, serverProperties: ServerProperties) : AbstractErrorController(errorAttributes) {
    val errorProperties: ErrorProperties = serverProperties.error

    private val errorAttributeName = DefaultErrorAttributes::class.java.name + ".ERROR"
    
    override fun getErrorPath(): String {
        return errorProperties.path
    }

    @ResponseBody
    @RequestMapping(produces = [APPLICATION_JSON_VALUE])
    fun jsonError(request: HttpServletRequest, response: HttpServletResponse): Any? {
        val errorAttributes = getErrorAttributes(request, false)
        val status = getStatus(request)
        response.status = status.value()
        return produceJsonResponse(request, errorAttributes)
    }

    private fun produceJsonResponse(request: HttpServletRequest, errorAttributes: Map<String, Any>): ResponseEntity<ErrorResponse> {
        val exception = extractException(request)

        return if ((exception != null) && MockException.NotFoundException::class.java.isAssignableFrom(exception::class.java)) {
            ResponseEntity.status(SC_NOT_FOUND).body(ErrorResponse("notFound", "details"))
        } else {
            val status = errorAttributes ["status"].toString()
            ResponseEntity.status(status.toInt()).body(ErrorResponse(status, errorAttributes ["error"].toString()))
        }
    }

    private fun extractException(request: HttpServletRequest): Exception? {
        return ServletRequestAttributes(request).getAttribute(errorAttributeName, SCOPE_REQUEST) as Exception?
    }
}