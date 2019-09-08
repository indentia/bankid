package indentia.bankid.mock.infrastructure.rest

import indentia.bankid.common.domain.IPAddress
import indentia.bankid.common.domain.PersonalNumber
import indentia.bankid.common.domain.RpV5Request
import indentia.bankid.common.domain.RpV5Response.AuthResponse
import uk.co.jemos.podam.api.AttributeMetadata
import uk.co.jemos.podam.api.DataProviderStrategy
import uk.co.jemos.podam.api.PodamFactory
import uk.co.jemos.podam.api.PodamFactoryImpl
import uk.co.jemos.podam.typeManufacturers.StringTypeManufacturerImpl
import java.lang.reflect.Type
import kotlin.reflect.KClass

class MockHelper {
    companion object {
        fun randomAuthResponse(): AuthResponse {
            return manufacturePojo(AuthResponse::class)
        }

        fun randomAuthRequest(): RpV5Request.AuthRequest {
            return manufacturePojo(RpV5Request.AuthRequest::class)
        }

        private fun <T : Any> manufacturePojo(kClass: KClass<T>): T {
            return podamFactory().manufacturePojo(kClass.java)
        }

        private fun podamFactory(): PodamFactory {
            val factory = PodamFactoryImpl()
            factory.strategy.addOrReplaceTypeManufacturer(String::class.java, PodamStringManufacturer())
            return factory
        }
    }

    class PodamStringManufacturer : StringTypeManufacturerImpl() {
        override fun getType(strategy: DataProviderStrategy,
                             attributeMetadata: AttributeMetadata,
                             genericTypesArgumentsMap: Map<String, Type>): String {

            if (IPAddress::class.java.isAssignableFrom(attributeMetadata.pojoClass)) {
                return IPAddress.random().value
            }
            if (PersonalNumber::class.java.isAssignableFrom(attributeMetadata.pojoClass)) {
                return PersonalNumber.random().value
            }
            if (RpV5Request.Requirement.CertificatePolicy::class.java.isAssignableFrom(attributeMetadata.pojoClass)) {
                return RpV5Request.Requirement.CertificatePolicy.random().value
            }
            return super.getType(strategy, attributeMetadata, genericTypesArgumentsMap)
        }
    }
}