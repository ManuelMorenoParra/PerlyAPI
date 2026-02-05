package data

import domain.MensajeDTO
import edu.gva.es.data.Bloqueos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.and

class MensajesDAO {

    fun insert(mensaje: MensajeDTO): Int = transaction {
        Mensajes.insert {
            it[idEmisor] = mensaje.idEmisor
            it[idReceptor] = mensaje.idReceptor
            it[this.mensaje] = mensaje.mensaje
            it[fecha] = LocalDateTime.now()
            it[leido] = false
        } get Mensajes.id
    }

    fun marcarLeido(id: Int): Boolean = transaction {
        Mensajes.update({ Mensajes.id eq id }) {
            it[leido] = true
        } > 0
    }

    fun delete(id: Int): Boolean = transaction {
        Mensajes.deleteWhere { Mensajes.id eq id } > 0
    }

    fun update(idParaBuscar: Int, dto: MensajeDTO): Int = transaction {
        // Usamos 'id' porque así llamaste a la columna id_mensaje en tu objeto Table
        Mensajes.update({ Mensajes.id eq idParaBuscar }) {
            it[mensaje] = dto.mensaje
            it[leido] = dto.leido
        }
    }

    fun getMensajesDeUsuario(idUsuarioActual: Int): List<MensajeDTO> = transaction {
        // 1. Obtenemos las listas de IDs de usuarios bloqueados
        val listaBloqueados = Bloqueos.selectAll()
            .where { Bloqueos.idBloqueador eq idUsuarioActual }
            .map { it[Bloqueos.idBloqueado] }

        val listaMeHanBloqueado = Bloqueos.selectAll()
            .where { Bloqueos.idBloqueado eq idUsuarioActual }
            .map { it[Bloqueos.idBloqueador] }

        val todosLosBloqueos = (listaBloqueados + listaMeHanBloqueado).distinct()

        // 2. Consulta principal
        Mensajes.selectAll()
            .where {
                (Mensajes.idReceptor eq idUsuarioActual) and
                        (Mensajes.idEmisor notInList todosLosBloqueos)
            }
            .map { row ->
                // ¡IMPORTANTE! Aquí es donde creamos el DTO para que no devuelva Unit
                MensajeDTO(
                    id = row[Mensajes.id],
                    idEmisor = row[Mensajes.idEmisor],
                    idReceptor = row[Mensajes.idReceptor],
                    mensaje = row[Mensajes.mensaje],
                    fecha = row[Mensajes.fecha].toString(),
                    leido = row[Mensajes.leido]
                )
            }
    }
}
