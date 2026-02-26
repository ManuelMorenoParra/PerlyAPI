package edu.gva.es.data

import edu.gva.es.domain.SeguidoresDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object SeguidoresDAO {

    fun seguir(idSeguidor: Int, idSeguido: Int) = transaction {
        Seguidores.insertIgnore {
            it[this.idSeguidor] = idSeguidor
            it[this.idSeguido] = idSeguido
            it[fechaSeguimiento] = LocalDateTime.now()
        }
    }

    fun dejarDeSeguir(idSeguidorParam: Int, idSeguidoParam: Int) = transaction {
        Seguidores.deleteWhere {
            (idSeguidor eq idSeguidorParam) and (idSeguido eq idSeguidoParam)
        }
    }

    fun contarSeguidores(idUsuario: Int): Long = transaction {
        Seguidores.selectAll().where { Seguidores.idSeguido eq idUsuario }.count()
    }

    fun contarSeguidos(idUsuario: Int): Long = transaction {
        Seguidores.selectAll().where { Seguidores.idSeguidor eq idUsuario }.count()
    }

    fun esSeguidor(idSeguidorParam: Int, idSeguidoParam: Int): Boolean = transaction {
        !Seguidores.selectAll().where {
            (Seguidores.idSeguidor eq idSeguidorParam) and (Seguidores.idSeguido eq idSeguidoParam)
        }.empty()
    }
}