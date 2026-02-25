package edu.gva.es.data

import edu.gva.es.domain.SoportesDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object SoportesDAO {
    fun crear(dto: SoportesDTO): Int = transaction {
        Soportes.insert {
            it[idUsuario] = dto.idUsuario
            it[asunto] = dto.asunto
            it[descripcion] = dto.descripcion
            // La fechaApertura se puede asignar aquí con LocalDateTime.now() si lo necesitas
        } get Soportes.id
    }

    fun listarPorUsuario(idUser: Int): List<SoportesDTO> = transaction {
        Soportes.selectAll().where { Soportes.idUsuario eq idUser }.map {
            SoportesDTO(
                id = it[Soportes.id],
                idUsuario = it[Soportes.idUsuario],
                asunto = it[Soportes.asunto],
                descripcion = it[Soportes.descripcion],
                respuesta = it[Soportes.respuesta],
                fechaApertura = it[Soportes.fechaApertura].toString(),
                fechaRespuesta = it[Soportes.fechaRespuesta]?.toString()
            )
        }
    }

    fun actualizar(idSop: Int, dto: SoportesDTO): Boolean = transaction {
        Soportes.update({ Soportes.id eq idSop }) {
            it[asunto] = dto.asunto
            it[descripcion] = dto.descripcion
        } > 0
    }

    fun eliminar(idSop: Int): Boolean = transaction {
        Soportes.deleteWhere { id eq idSop } > 0
    }

    fun responder(idSop: Int, textoRespuesta: String) = transaction {

        val soporte = Soportes.selectAll().where { Soportes.id eq idSop }.singleOrNull()
        if (soporte != null) {
            val descripcionAntigua = soporte[Soportes.descripcion]
            Soportes.update({ Soportes.id eq idSop }) {
                it[descripcion] = "$descripcionAntigua\nRESPUESTA: $textoRespuesta"
            }
        }
    }
}