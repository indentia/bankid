package indentia.bankid.common.domain

import org.testng.annotations.Test

class CertificatePolicyTest {

    @Test
    fun testConstruct() {
        CertificatePolicy.validOids.forEach { CertificatePolicy(it.replace("\\", "")) }
    }
}