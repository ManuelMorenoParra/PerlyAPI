package edu.gva.es.data

import edu.gva.es.domain.SeguidorDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object SeguidoresDAO {

    fun seguir(dto: SeguidorDTO) = transaction {
        Seguidores.insert {
            it[idUsuario] = dto.idUsuario
            it[idSeguido] = dto.idSeguido
            it[fecha] = LocalDateTime.now()
        }
    }

    fun dejarDeSeguir(idUsuario: Int, idSeguido: Int) = transaction {
        Seguidores.deleteWhere {
            (Seguidores.idUsuario eq idUsuario) and
                    (Seguidores.idSeguido eq idSeguido)
        }
    }

    fun obtenerSeguidores(idUsuario: Int) = transaction {
        Seguidores.selectAll()
            .where { Seguidores.idSeguido eq idUsuario }
            .map { it[Seguidores.idUsuario] }
    }
}
