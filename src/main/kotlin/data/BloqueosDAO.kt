package edu.gva.es.data

import domain.BloqueoDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object BloqueosDAO {

    // Bloquear a alguien
    fun insert(bloqueo: BloqueoDTO): Int = transaction {
        Bloqueos.insert {
            it[idBloqueador] = bloqueo.idBloqueador
            it[idBloqueado] = bloqueo.idBloqueado
            it[fecha] = LocalDateTime.now()
        } get Bloqueos.id
    }

    // Desbloquear (Borrar la fila)
    fun delete(idBloqueador: Int, idBloqueado: Int): Int = transaction {
        Bloqueos.deleteWhere {
            (Bloqueos.idBloqueador eq idBloqueador) and (Bloqueos.idBloqueado eq idBloqueado)
        }
    }

    // Comprobar si A tiene bloqueado a B
    fun estaBloqueado(idBloqueador: Int, idBloqueado: Int): Boolean = transaction {
        Bloqueos.selectAll()
            .where { (Bloqueos.idBloqueador eq idBloqueador) and (Bloqueos.idBloqueado eq idBloqueado) }
            .count() > 0
    }
}