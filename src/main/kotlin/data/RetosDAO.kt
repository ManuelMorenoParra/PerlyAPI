package edu.gva.es.data

import edu.gva.es.domain.RetoDTO // Import corregido
import edu.gva.es.domain.Retos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object RetosDAO {
    fun obtenerTodos(): List<RetoDTO> = transaction {
        Retos.selectAll().map {
            RetoDTO(
                id = it[Retos.id],
                nombre = it[Retos.nombre],
                descripcion = it[Retos.descripcion],
                puntos = it[Retos.puntos]
            )
        }
    }

    fun obtenerPorId(idReto: Int): RetoDTO? = transaction {
        Retos.selectAll().where { Retos.id eq idReto }.map {
            RetoDTO(
                id = it[Retos.id],
                nombre = it[Retos.nombre],
                descripcion = it[Retos.descripcion],
                puntos = it[Retos.puntos]
            )
        }.singleOrNull()
    }

    fun insertar(dto: RetoDTO): Int = transaction {
        Retos.insert {
            it[nombre] = dto.nombre
            it[descripcion] = dto.descripcion
            it[puntos] = dto.puntos
        } get Retos.id
    }

    fun actualizar(idReto: Int, dto: RetoDTO): Boolean = transaction {
        Retos.update({ Retos.id eq idReto }) {
            it[nombre] = dto.nombre
            it[descripcion] = dto.descripcion
            it[puntos] = dto.puntos
        } > 0
    }

    fun eliminar(idReto: Int): Boolean = transaction {
        Retos.deleteWhere { id eq idReto } > 0
    }
}