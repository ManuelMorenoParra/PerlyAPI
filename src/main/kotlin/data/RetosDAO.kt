package edu.gva.es.data

import edu.gva.es.domain.RetosDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object RetosDAO {
    private fun rowToDto(it: ResultRow) = RetosDTO(
        id = it[Retos.id],
        titulo = it[Retos.titulo],
        descripcion = it[Retos.descripcion],
        categoria = it[Retos.categoria],
        puntos = it[Retos.puntos],
        esDiario = it[Retos.esDiario]
    )

    fun obtenerTodos(): List<RetosDTO> = transaction {
        Retos.selectAll().map { rowToDto(it) }
    }

    fun obtenerPorId(idReto: Int): RetosDTO? = transaction {
        Retos.selectAll().where { Retos.id eq idReto }.map { rowToDto(it) }.singleOrNull()
    }

    fun insertar(dto: RetosDTO): Int = transaction {
        Retos.insert {
            it[titulo] = dto.titulo
            it[descripcion] = dto.descripcion
            it[categoria] = dto.categoria
            it[puntos] = dto.puntos
            it[esDiario] = dto.esDiario
        } get Retos.id
    }

    fun actualizar(idReto: Int, dto: RetosDTO): Boolean = transaction {
        Retos.update({ Retos.id eq idReto }) {
            it[titulo] = dto.titulo
            it[descripcion] = dto.descripcion
            it[categoria] = dto.categoria
            it[puntos] = dto.puntos
            it[esDiario] = dto.esDiario
        } > 0
    }

    fun eliminar(idReto: Int): Boolean = transaction {
        Retos.deleteWhere { id eq idReto } > 0
    }
}