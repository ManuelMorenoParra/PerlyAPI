package data

import domain.ProgresoDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq // Importante para deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import data.Progreso

class ProgresoDAO {

    fun getByUsuario(idUsuario: Int): List<ProgresoDTO> = transaction {
        Progreso.selectAll() // Cambiado select por selectAll().where en versiones nuevas de Exposed
            .where { Progreso.idUsuario eq idUsuario }
            .map {
                ProgresoDTO(
                    id = it[Progreso.id],
                    idUsuario = it[Progreso.idUsuario],
                    idReto = it[Progreso.idReto],
                    puntosGanados = it[Progreso.puntosGanados],
                    fecha = it[Progreso.fecha].toString(),
                    completado = it[Progreso.completado]
                )
            }
    }

    fun insert(progreso: ProgresoDTO): Int = transaction {
        Progreso.insert {
            it[idUsuario] = progreso.idUsuario
            it[idReto] = progreso.idReto
            it[puntosGanados] = progreso.puntosGanados
            it[fecha] = LocalDate.now()
            it[completado] = progreso.completado
        } get Progreso.id
    }

    fun yaCompletado(idUsuario: Int, idReto: Int): Boolean = transaction {
        Progreso.selectAll()
            .where { (Progreso.idUsuario eq idUsuario) and (Progreso.idReto eq idReto) }
            .count() > 0
    }

    fun totalPuntosUsuario(idUsuario: Int): Int = transaction {
        Progreso.slice(Progreso.puntosGanados.sum())
            .selectAll()
            .where { Progreso.idUsuario eq idUsuario }
            .map { it[Progreso.puntosGanados.sum()] ?: 0 }
            .first()
    }

    fun delete(idProgreso: Int): Int = transaction {
        Progreso.deleteWhere { Progreso.id eq idProgreso }
    }

    fun actualizar(idProgreso: Int, dto: ProgresoDTO): Boolean = transaction {
        Progreso.update({ Progreso.id eq idProgreso }) {
            it[idUsuario] = dto.idUsuario
            it[idReto] = dto.idReto
            it[puntosGanados] = dto.puntosGanados
            it[fecha] = LocalDate.parse(dto.fecha)
            it[completado] = dto.completado
        } > 0
    }
}