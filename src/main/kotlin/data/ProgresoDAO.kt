package edu.gva.es.data

import edu.gva.es.domain.ProgresoDTO
import edu.gva.es.domain.Progresos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import edu.gva.es.domain.*

object ProgresosDAO {
    fun getByUsuario(idUser: Int): List<ProgresoDTO> = transaction {
        Progresos.selectAll().where { Progresos.idUsuario eq idUser }.map {
            ProgresoDTO(it[Progresos.id], it[Progresos.idUsuario], it[Progresos.idReto], it[Progresos.puntosGanados], it[Progresos.fecha].toString(), it[Progresos.completado])
        }
    }

    fun insert(p: ProgresoDTO): Int = transaction {
        Progresos.insert {
            it[idUsuario] = p.idUsuario
            it[idReto] = p.idReto
            it[puntosGanados] = p.puntosGanados
            it[fecha] = LocalDateTime.now()
            it[completado] = p.completado
        } get Progresos.id
    }

    fun yaCompletado(idUser: Int, idR: Int): Boolean = transaction {
        Progresos.selectAll().where { (Progresos.idUsuario eq idUser) and (Progresos.idReto eq idR) }.count() > 0
    }

    fun totalPuntosUsuario(idUser: Int): Int = transaction {
        val sumExp = Progresos.puntosGanados.sum()
        Progresos.select(sumExp).where { Progresos.idUsuario eq idUser }.map { it[sumExp] ?: 0 }.firstOrNull() ?: 0
    }

    fun actualizar(idProg: Int, p: ProgresoDTO): Boolean = transaction {
        Progresos.update({ Progresos.id eq idProg }) {
            it[puntosGanados] = p.puntosGanados
            it[completado] = p.completado
        } > 0
    }

    fun delete(idProg: Int): Int = transaction {
        Progresos.deleteWhere { id eq idProg }
    }
}