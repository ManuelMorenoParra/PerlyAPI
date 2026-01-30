package data

import domain.ProgresoDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

class ProgresoDAO {

    fun getByUsuario(idUsuario: Int): List<ProgresoDTO> = transaction {
        Progreso.select { Progreso.idUsuario eq idUsuario }
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
            it[fecha] = LocalDate.now() // LocalDate para tipo "date" de SQL
            it[completado] = progreso.completado
        } get Progreso.id
    }

    fun yaCompletado(idUsuario: Int, idReto: Int): Boolean = transaction {
        Progreso.select {
            (Progreso.idUsuario eq idUsuario) and
                    (Progreso.idReto eq idReto)
        }.count() > 0
    }

    fun totalPuntosUsuario(idUsuario: Int): Int = transaction {
        Progreso.slice(Progreso.puntosGanados.sum())
            .select { Progreso.idUsuario eq idUsuario }
            .map { it[Progreso.puntosGanados.sum()] ?: 0 }
            .first()
    }
}
