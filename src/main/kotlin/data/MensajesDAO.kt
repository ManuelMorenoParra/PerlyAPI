package data

import domain.MensajeDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class MensajesDAO {

    fun getMensajesDeUsuario(idUsuario: Int): List<MensajeDTO> = transaction {
        Mensajes.select {
            (Mensajes.idEmisor eq idUsuario) or
                    (Mensajes.idReceptor eq idUsuario)
        }.map {
            MensajeDTO(
                id = it[Mensajes.id],
                idEmisor = it[Mensajes.idEmisor],
                idReceptor = it[Mensajes.idReceptor],
                mensaje = it[Mensajes.mensaje],
                fecha = it[Mensajes.fecha],
                leido = it[Mensajes.leido]
            )
        }
    }

    fun insert(mensaje: MensajeDTO): Int = transaction {
        Mensajes.insert {
            it[idEmisor] = mensaje.idEmisor
            it[idReceptor] = mensaje.idReceptor
            it[this.mensaje] = mensaje.mensaje
            it[fecha] = java.time.LocalDateTime.now().toString()
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
}
