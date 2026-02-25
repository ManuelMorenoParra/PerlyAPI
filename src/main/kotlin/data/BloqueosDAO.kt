package edu.gva.es.data

import edu.gva.es.domain.BloqueoDTO // Import corregido
import edu.gva.es.domain.Bloqueos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object BloqueosDAO {
    fun insertar(dto: BloqueoDTO): Int = transaction {
        Bloqueos.insert {
            it[idBloqueador] = dto.idBloqueador
            it[idBloqueado] = dto.idBloqueado
        } get Bloqueos.id
    }

    fun eliminar(bloqueador: Int, bloqueado: Int): Boolean = transaction {
        Bloqueos.deleteWhere {
            (idBloqueador eq bloqueador) and (idBloqueado eq bloqueado)
        } > 0
    }

    fun obtenerTodos(): List<BloqueoDTO> = transaction {
        Bloqueos.selectAll().map {
            BloqueoDTO(
                id = it[Bloqueos.id],
                idBloqueador = it[Bloqueos.idBloqueador],
                idBloqueado = it[Bloqueos.idBloqueado]
            )
        }
    }
}