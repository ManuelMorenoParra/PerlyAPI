import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class SoporteTest {

    @Test
    fun testSoporte() = testApplication {
        val response = client.get("/soporte/1")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
