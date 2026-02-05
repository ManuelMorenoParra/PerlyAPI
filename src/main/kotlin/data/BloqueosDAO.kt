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
        } get Bloqueos.id
    }

    // Desbloquear (Borrar la fila)
    fun delete(bloqueador: Int, bloqueado: Int): Boolean = transaction {
        Bloqueos.deleteWhere {
            (Bloqueos.idBloqueador eq bloqueador) and (Bloqueos.idBloqueado eq bloqueado)
        } > 0
    }

    // Comprobar si A tiene bloqueado a B
    fun estaBloqueado(idBloqueador: Int, idBloqueado: Int): Boolean = transaction {
        Bloqueos.selectAll()
            .where { (Bloqueos.idBloqueador eq idBloqueador) and (Bloqueos.idBloqueado eq idBloqueado) }
            .count() > 0
    }

    fun getAll(): List<BloqueoDTO> = transaction {
        Bloqueos.selectAll().map {
            BloqueoDTO(it[Bloqueos.id], it[Bloqueos.idBloqueador], it[Bloqueos.idBloqueado])
        }
    }

    fun bloquearUsuario(dto: BloqueoDTO): Int = BloqueosDAO.insert(dto)

    fun desbloquearUsuario(idBloqueador: Int, idBloqueado: Int): Boolean =
        BloqueosDAO.delete(idBloqueador, idBloqueado)

    fun obtenerTodos() = BloqueosDAO.getAll()
}