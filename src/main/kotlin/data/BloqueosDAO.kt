package edu.gva.es.data

import edu.gva.es.domain.BloqueosDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object BloqueosDAO {

    fun bloquear(dto: BloqueosDTO): Int = transaction {
        Bloqueos.insert {
            it[idBloqueador] = dto.idBloqueador
            it[idBloqueado] = dto.idBloqueado
            it[tipo] = dto.tipo
            it[fecha] = LocalDateTime.now()
        } get Bloqueos.id
    }

    fun obtenerBloqueadosPorUsuario(idUser: Int): List<BloqueosDTO> = transaction {

        val join = Bloqueos.innerJoin(Usuarios, { Bloqueos.idBloqueado }, { Usuarios.id })
        
        join.select { Bloqueos.idBloqueador eq idUser }
            .map { row ->
                BloqueosDTO(
                    id = row[Bloqueos.id],
                    idBloqueador = row[Bloqueos.idBloqueador],
                    idBloqueado = row[Bloqueos.idBloqueado],
                    tipo = row[Bloqueos.tipo],
                    fecha = row[Bloqueos.fecha].toString(),
                    name = row[Usuarios.nombre],
                    avatar = row[Usuarios.avatar]
                )
            }
    }

    fun eliminarBloqueo(idBloqueador: Int, idBloqueado: Int, tipo: String): Boolean = transaction {
        Bloqueos.deleteWhere {
            (Bloqueos.idBloqueador eq idBloqueador) and
                    (Bloqueos.idBloqueado eq idBloqueado) and
                    (Bloqueos.tipo eq tipo)
        } > 0
    }
}