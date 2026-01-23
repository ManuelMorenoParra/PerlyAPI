import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class LikesTest {

    @Test
    fun testContarLikes() = testApplication {
        val response = client.get("/likes/1")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
