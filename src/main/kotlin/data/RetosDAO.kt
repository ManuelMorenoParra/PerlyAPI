package edu.gva.es.data

import edu.gva.es.domain.RetosDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object RetosDAO {
    fun obtenerTodos(): List<RetosDTO> = transaction {
        Retos.selectAll().map {
            RetosDTO(
                id = it[Retos.id_reto],
                titulo = it[Retos.titulo],
                descripcion = it[Retos.descripcion],
                puntos = it[Retos.puntos]
            )
        }
    }

    fun obtenerPorId(idReto: Int): RetosDTO? = transaction {
        Retos.selectAll().where { Retos.id_reto eq idReto }.map {
            RetosDTO(
                id = it[Retos.id_reto],
                titulo = it[Retos.titulo],
                descripcion = it[Retos.descripcion],
                puntos = it[Retos.puntos]
            )
        }.singleOrNull()
    }

    fun insertar(dto: RetosDTO): Int = transaction {
        Retos.insert {
            it[titulo] = dto.titulo
            it[descripcion] = dto.descripcion
            it[puntos] = dto.puntos
        } get Retos.id_reto
    }

    fun actualizar(idReto: Int, dto: RetosDTO): Boolean = transaction {
        Retos.update({ Retos.id_reto eq idReto }) {
            it[titulo] = dto.titulo
            it[descripcion] = dto.descripcion
            it[puntos] = dto.puntos
        } > 0
    }

    fun eliminar(idReto: Int): Boolean = transaction {
        Retos.deleteWhere { id_reto eq idReto } > 0
    }
}