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
        // Unimos la tabla Bloqueos con Usuarios donde Bloqueos.idBloqueado == Usuarios.id
        (Bloqueos innerJoin Usuarios)
            .select { Bloqueos.idBloqueador eq idUser }
            .map {
                BloqueosDTO(
                    id = it[Bloqueos.id],
                    idBloqueador = it[Bloqueos.idBloqueador],
                    idBloqueado = it[Bloqueos.idBloqueado],
                    tipo = it[Bloqueos.tipo],
                    fecha = it[Bloqueos.fecha].toString(),
                    // Extraemos los datos de la tabla Usuarios
                    name = it[Usuarios.nombre], // o Usuarios.username, como lo tengas
                    avatar = it[Usuarios.avatar]
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