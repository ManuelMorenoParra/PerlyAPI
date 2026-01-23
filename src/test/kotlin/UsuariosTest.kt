import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class UsuariosTest {

    @Test
    fun testObtenerUsuarios() = testApplication {
        val response = client.get("/usuarios")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
