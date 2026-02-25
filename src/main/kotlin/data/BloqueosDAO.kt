package edu.gva.es.data

import edu.gva.es.domain.BloqueosDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object BloqueosDAO {
    fun insert(dto: BloqueosDTO): Int = transaction {
        Bloqueos.insert {
            it[idBloqueador] = dto.idBloqueador
            it[idBloqueado] = dto.idBloqueado
        } get Bloqueos.id
    }

    fun delete(bloqueador: Int, bloqueado: Int): Boolean = transaction {
        Bloqueos.deleteWhere {
            (idBloqueador eq bloqueador) and (idBloqueado eq bloqueado)
        } > 0
    }

    fun getAll(): List<BloqueosDTO> = transaction {
        Bloqueos.selectAll().map {
            BloqueosDTO(
                id = it[Bloqueos.id],
                idBloqueador = it[Bloqueos.idBloqueador],
                idBloqueado = it[Bloqueos.idBloqueado]
            )
        }
    }
}