package indentia.bankid.common.domain

import indentia.bankid.common.domain.RpV5Request.Requirement.CertificatePolicy.Companion.ValidOids
import org.testng.annotations.Test

class CertificatePolicyTest {

    @Test
    fun testConstruct() {
        ValidOids.forEach { RpV5Request.Requirement.CertificatePolicy(it.replace("\\", "")) }
    }
}