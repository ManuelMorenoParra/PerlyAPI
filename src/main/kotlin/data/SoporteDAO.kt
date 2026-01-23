package edu.gva.es.data

import edu.gva.es.domain.SoporteDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object SoporteDAO {

    fun crear(dto: SoporteDTO) = transaction {
        Soporte.insert {
            it[idUsuario] = dto.idUsuario
            it[asunto] = dto.asunto
            it[descripcion] = dto.descripcion
            it[estado] = "ABIERTO"
            it[fechaApertura] = LocalDateTime.now()
        }
    }

    fun responder(id: Int, respuesta: String) = transaction {
        Soporte.update({ Soporte.id eq id }) {
            it[Soporte.respuesta] = respuesta
            it[estado] = "CERRADO"
            it[fechaRespuesta] = LocalDateTime.now()
        }
    }

    fun listarPorUsuario(idUsuario: Int) = transaction {
        Soporte.selectAll()
            .where { Soporte.idUsuario eq idUsuario }
            .map { it[Soporte.asunto] }
    }
}
