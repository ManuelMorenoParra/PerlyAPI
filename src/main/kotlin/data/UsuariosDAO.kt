package edu.gva.es.data

import edu.gva.es.domain.UsuarioDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object UsuariosDAO {

    private fun ResultRow.toUsuarioDTO() = UsuarioDTO(
        id = this[Usuarios.idUsuario],
        nombre = this[Usuarios.nombre],
        email = this[Usuarios.email],
        password = this[Usuarios.password],
        fechaNacimiento = this[Usuarios.fechaNacimiento]
    )

    fun insertar(u: UsuarioDTO): Int = transaction {
        Usuarios.insert {
            it[nombre] = u.nombre
            it[email] = u.email
            it[password] = u.password
            it[fechaNacimiento] = u.fechaNacimiento
        } get Usuarios.idUsuario
    }

    fun actualizar(id: Int, u: UsuarioDTO): Int = transaction {
        Usuarios.update({ Usuarios.idUsuario eq id }) {
            it[nombre] = u.nombre
            it[email] = u.email
            it[password] = u.password
            it[fechaNacimiento] = u.fechaNacimiento
        }
    }

    fun seleccionarTodos(): List<UsuarioDTO> = transaction {
        Usuarios.selectAll().map { it.toUsuarioDTO() }
    }

    fun seleccionarPorId(id: Int): UsuarioDTO? = transaction {
        Usuarios.select { Usuarios.idUsuario eq id }
            .map { it.toUsuarioDTO() }
            .singleOrNull()
    }

    fun eliminar(id: Int): Int = transaction {
        Usuarios.deleteWhere { Usuarios.idUsuario eq id }
    }

    fun seleccionarPorEmail(email: String): UsuarioDTO? = transaction {
        Usuarios.select { Usuarios.email eq email }
            .map { it.toUsuarioDTO() }
            .singleOrNull()
    }
}
