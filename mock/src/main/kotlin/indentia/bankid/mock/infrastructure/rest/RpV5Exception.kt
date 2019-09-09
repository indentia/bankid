package indentia.bankid.mock.infrastructure.rest

open class RpV5Exception(val httpStatus: Int, val code: String, message: String) : RuntimeException(message)
