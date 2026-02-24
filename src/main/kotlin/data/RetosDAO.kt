package edu.gva.es.data

import domain.RetoDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import edu.gva.es.domain.*

object RetosDAO {

    fun crearReto(reto: RetoDTO): RetoDTO {
        var idGenerado: Int? = null
        transaction {
            idGenerado = Retos.insert {
                it[titulo] = reto.titulo
                it[descripcion] = reto.descripcion
                it[puntos] = reto.puntos
            }[Retos.id_reto]
        }
        return reto.copy(id = idGenerado)
    }

    fun obtenerTodos(): List<RetoDTO> = transaction {
        Retos.selectAll().map {
            RetoDTO(
                id = it[Retos.id_reto],
                titulo = it[Retos.titulo],
                descripcion = it[Retos.descripcion],
                puntos = it[Retos.puntos]
            )
        }
    }

    fun obtenerPorId(id: Int): RetoDTO? = transaction {
        Retos.select { Retos.id_reto eq id }
            .map {
                RetoDTO(
                    id = it[Retos.id_reto],
                    titulo = it[Retos.titulo],
                    descripcion = it[Retos.descripcion],
                    puntos = it[Retos.puntos]
                )
            }
            .singleOrNull()
    }

    fun actualizarReto(reto: RetoDTO): Boolean = transaction {
        val updated = Retos.update({ Retos.id_reto eq (reto.id ?: 0) }) {
            it[titulo] = reto.titulo
            it[descripcion] = reto.descripcion
            it[puntos] = reto.puntos
        }
        updated > 0
    }

    fun eliminarReto(id: Int): Boolean = transaction {
        val deleted = Retos.deleteWhere { Retos.id_reto eq id }
        deleted > 0
    }
}
