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

    fun dejarDeSeguir(idUsuario: Int, idSeguido: Int): Int = transaction {
        Seguidores.deleteWhere {
            (Seguidores.idUsuario eq idUsuario) and
                    (Seguidores.idSeguido eq idSeguido)
        }
    }

    // Añadimos el tipo de retorno explícito para evitar errores de inferencia
    fun obtenerSeguidores(idUsuario: Int): List<Int> = transaction {
        Seguidores.selectAll()
            .where { Seguidores.idSeguido eq idUsuario }
            .map { it[Seguidores.idUsuario] as Int }
    }

    fun actualizar(idSeguimiento: Int, dto: SeguidorDTO): Boolean = transaction {
        Seguidores.update({ Seguidores.id eq idSeguimiento }) {
            it[idUsuario] = dto.idUsuario
            it[idSeguido] = dto.idSeguido
            it[fecha] = LocalDateTime.now() // Actualizamos la fecha al momento del cambio
        } > 0
    }
}