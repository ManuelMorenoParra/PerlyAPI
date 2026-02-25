package edu.gva.es.data

import edu.gva.es.domain.SoporteDTO
import edu.gva.es.domain.Soportes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object SoportesDAO {
    fun crear(dto: SoporteDTO): Int = transaction {
        Soportes.insert {
            it[idUsuario] = dto.idUsuario
            it[asunto] = dto.asunto
            it[mensaje] = dto.mensaje
            it[estado] = dto.estado ?: "PENDIENTE"
        } get Soportes.id
    }

    fun listarPorUsuario(idUser: Int): List<SoporteDTO> = transaction {
        Soportes.selectAll().where { Soportes.idUsuario eq idUser }.map {
            SoporteDTO(
                id = it[Soportes.id],
                idUsuario = it[Soportes.idUsuario],
                asunto = it[Soportes.asunto],
                mensaje = it[Soportes.mensaje],
                estado = it[Soportes.estado]
            )
        }
    }

    fun actualizar(idSop: Int, dto: SoporteDTO): Boolean = transaction {
        Soportes.update({ Soportes.id eq idSop }) {
            it[asunto] = dto.asunto
            it[mensaje] = dto.mensaje
            it[estado] = dto.estado ?: "PENDIENTE"
        } > 0
    }

    fun eliminar(idSop: Int): Boolean = transaction {
        Soportes.deleteWhere { id eq idSop } > 0
    }

    fun responder(idSop: Int, respuesta: String) = transaction {
        Soportes.update({ Soportes.id eq idSop }) {
            it[mensaje] = it[mensaje] + "\nRESPUESTA: $respuesta"
            it[estado] = "RESPONDIDO"
        }
    }
}