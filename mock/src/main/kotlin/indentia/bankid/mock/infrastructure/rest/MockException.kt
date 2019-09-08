package indentia.bankid.mock.infrastructure.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

open class MockException : RuntimeException() {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    class InternalServerErrorException : MockException()

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class NotFoundException : MockException()
}
