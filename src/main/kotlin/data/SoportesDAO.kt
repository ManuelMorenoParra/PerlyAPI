package edu.gva.es.data

import edu.gva.es.domain.SoporteDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object SoportesDAO {

    fun crear(dto: SoporteDTO) = transaction {
        Soportes.insert {
            it[idUsuario] = dto.idUsuario
            it[asunto] = dto.asunto
            it[descripcion] = dto.descripcion
            it[estado] = "ABIERTO"
            it[fechaApertura] = LocalDateTime.now()
        }
    }

    fun responder(id: Int, respuesta: String) = transaction {
        Soportes.update({ Soportes.id eq id }) {
            it[Soportes.respuesta] = respuesta
            it[estado] = "CERRADO"
            it[fechaRespuesta] = LocalDateTime.now()
        }
    }

    fun listarPorUsuario(idUsuario: Int) = transaction {
        Soportes.selectAll()
            .where { Soportes.idUsuario eq idUsuario }
            .map { it[Soportes.asunto] }
    }

    fun actualizar(idSoporte: Int, dto: SoporteDTO): Boolean = transaction {
        Soportes.update({ Soportes.id eq idSoporte }) {

            it[Soportes.asunto] = dto.asunto
            it[Soportes.descripcion] = dto.descripcion
            it[Soportes.estado] = dto.estado

        } > 0
    }
}
