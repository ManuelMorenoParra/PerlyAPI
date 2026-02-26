package edu.gva.es.data

import edu.gva.es.domain.SoportesDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object SoportesDAO {

    private fun ResultRow.toDTO() = SoportesDTO(
        id = this[Soportes.id],
        idUsuario = this[Soportes.idUsuario],
        asunto = this[Soportes.asunto],
        descripcion = this[Soportes.descripcion],
        respuesta = this[Soportes.respuesta],
        estado = this[Soportes.estado],
        fechaApertura = this[Soportes.fechaApertura].toString(),
        fechaRespuesta = this[Soportes.fechaRespuesta]?.toString()
    )

    fun insertar(s: SoportesDTO): Int = transaction {
        Soportes.insert {
            it[idUsuario] = s.idUsuario
            it[asunto] = s.asunto
            it[descripcion] = s.descripcion
            it[estado] = s.estado ?: "open" // Aseguramos un valor
            it[fechaApertura] = LocalDateTime.now() // Esto debería solucionar el error del log
        } get Soportes.id
    }

    fun listarPorUsuario(idUser: Int): List<SoportesDTO> = transaction {
        Soportes.selectAll().where { Soportes.idUsuario eq idUser }
            .orderBy(Soportes.fechaApertura, SortOrder.DESC)
            .map { it.toDTO() }
    }

    fun actualizarEstado(idTicket: Int, nuevoEstado: String, resp: String? = null) = transaction {
        Soportes.update({ Soportes.id eq idTicket }) {
            it[estado] = nuevoEstado
            if (resp != null) {
                it[respuesta] = resp
                it[fechaRespuesta] = LocalDateTime.now()
            }
        }
    }
}