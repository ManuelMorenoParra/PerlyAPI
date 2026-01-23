import edu.gva.es.core.ConexionDB
import kotlin.test.Test
import kotlin.test.assertNotNull

class ConexionDBTest {

    @Test
    fun testConexion() {
        ConexionDB.conectar()
        assertNotNull(ConexionDB.db)
    }
}
