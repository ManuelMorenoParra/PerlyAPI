package edu.gva.es.data

import edu.gva.es.domain.SeguidoresDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object SeguidoresDAO {
    fun seguir(dto: SeguidoresDTO) = transaction {
        Seguidores.insert {
            it[idUsuario] = dto.idUsuario
            it[idSeguido] = dto.idSeguido
        } get Seguidores.id
    }

    fun dejarDeSeguir(u: Int, s: Int): Int = transaction {
        Seguidores.deleteWhere { (idUsuario eq u) and (idSeguido eq s) }
    }

    fun obtenerSeguidores(idUser: Int): List<Int> = transaction {
        Seguidores.selectAll().where { Seguidores.idSeguido eq idUser }
            .map { it[Seguidores.idUsuario] }
    }

    fun actualizar(idReg: Int, dto: SeguidoresDTO): Boolean = transaction {
        Seguidores.update({ Seguidores.id eq idReg }) {
            it[idUsuario] = dto.idUsuario
            it[idSeguido] = dto.idSeguido
        } > 0
    }
}