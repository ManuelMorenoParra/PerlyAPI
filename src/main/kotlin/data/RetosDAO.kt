package data

import domain.RetoDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class RetosDAO {

    fun getAll(): List<RetoDTO> = transaction {
        Retos.selectAll().map {
            RetoDTO(
                id = it[Retos.id],
                titulo = it[Retos.titulo],
                descripcion = it[Retos.descripcion],
                puntos = it[Retos.puntos]
            )
        }
    }

    fun getById(id: Int): RetoDTO? = transaction {
        Retos.select { Retos.id eq id }
            .map {
                RetoDTO(
                    id = it[Retos.id],
                    titulo = it[Retos.titulo],
                    descripcion = it[Retos.descripcion],
                    puntos = it[Retos.puntos]
                )
            }.singleOrNull()
    }

    fun insert(reto: RetoDTO): Int = transaction {
        Retos.insert {
            it[titulo] = reto.titulo
            it[descripcion] = reto.descripcion
            it[puntos] = reto.puntos
        } get Retos.id
    }

    fun update(id: Int, reto: RetoDTO): Boolean = transaction {
        Retos.update({ Retos.id eq id }) {
            it[titulo] = reto.titulo
            it[descripcion] = reto.descripcion
            it[puntos] = reto.puntos
        } > 0
    }

    fun delete(id: Int): Boolean = transaction {
        Retos.deleteWhere { Retos.id eq id } > 0
    }
}
