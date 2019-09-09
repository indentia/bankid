package indentia.bankid.mock.infrastructure.rest

import javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR
import javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

open class MockException(val httpStatus: Int, val code: String, message: String) : RuntimeException(message) {
    class InternalErrorException(message: String) : MockException(SC_INTERNAL_SERVER_ERROR, "internalError", message)
    class NotFoundException(message: String) : MockException(SC_NOT_FOUND, "notFound", message)
}
