package edu.gva.es.data

import edu.gva.es.domain.UsuarioDTO
import edu.gva.es.domain.Usuarios
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import edu.gva.es.domain.*

object UsuariosDAO {
    private fun ResultRow.toDTO() = UsuarioDTO(
        id = this[Usuarios.id],
        nombre = this[Usuarios.nombre],
        email = this[Usuarios.email],
        password = this[Usuarios.password]
    )

    fun seleccionarTodos(): List<UsuarioDTO> = transaction {
        Usuarios.selectAll().map { it.toDTO() }
    }

    fun seleccionarPorId(id: Int): UsuarioDTO? = transaction {
        Usuarios.selectAll().where { Usuarios.id eq id }.map { it.toDTO() }.singleOrNull()
    }

    fun seleccionarPorEmail(email: String): UsuarioDTO? = transaction {
        Usuarios.selectAll().where { Usuarios.email eq email }.map { it.toDTO() }.singleOrNull()
    }

    fun insertar(u: UsuarioDTO): Int = transaction {
        Usuarios.insert {
            it[nombre] = u.nombre
            it[email] = u.email
            it[password] = u.password ?: ""
        } get Usuarios.id
    }

    fun actualizar(idUsuario: Int, u: UsuarioDTO): Int = transaction {
        Usuarios.update({ Usuarios.id eq idUsuario }) {
            it[nombre] = u.nombre
            it[email] = u.email
            if (u.password != null) it[password] = u.password
        }
    }

    fun eliminar(idUsuario: Int): Int = transaction {
        Usuarios.deleteWhere { id eq idUsuario }
    }
}