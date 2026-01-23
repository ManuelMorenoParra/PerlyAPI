import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class PublicacionesTest {

    @Test
    fun testPublicaciones() = testApplication {
        val response = client.get("/publicaciones")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
