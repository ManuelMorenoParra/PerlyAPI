package edu.gva.es.data

import edu.gva.es.domain.UsuarioDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate

object UsuariosDAO {

    // Cambiado 'it' por 'this' y ajustado a 'id' (como estaba en tu objeto Usuarios)
    private fun ResultRow.toUsuarioDTO() = UsuarioDTO(
        id = this[Usuarios.id],
        nombre = this[Usuarios.nombre],
        email = this[Usuarios.email],
        password = this[Usuarios.password],
        fechaNacimiento = this[Usuarios.fechaNacimiento]?.toString()
    )

    fun insertar(u: UsuarioDTO): Int = transaction {
        Usuarios.insert {
            it[nombre] = u.nombre
            it[email] = u.email
            it[password] = u.password ?: ""
            // Conversión de String a LocalDate
            it[fechaNacimiento] = u.fechaNacimiento?.let { fecha -> LocalDate.parse(fecha) }
        } get Usuarios.id
    }

    fun actualizar(idUsuario: Int, u: UsuarioDTO): Int = transaction {
        Usuarios.update({ Usuarios.id eq idUsuario }) {
            it[nombre] = u.nombre
            it[email] = u.email
            if (u.password != null) it[password] = u.password
            it[fechaNacimiento] = u.fechaNacimiento?.let { fecha -> LocalDate.parse(fecha) }
        }
    }

    fun seleccionarTodos(): List<UsuarioDTO> = transaction {
        Usuarios.selectAll().map { it.toUsuarioDTO() }
    }

    fun seleccionarPorId(idUsuario: Int): UsuarioDTO? = transaction {
        Usuarios.selectAll().where { Usuarios.id eq idUsuario }
            .map { it.toUsuarioDTO() }
            .singleOrNull()
    }

    fun eliminar(idUsuario: Int): Int = transaction {
        Usuarios.deleteWhere { id eq idUsuario }
    }

    fun seleccionarPorEmail(email: String): UsuarioDTO? = transaction {
        Usuarios.selectAll().where { Usuarios.email eq email }
            .map { it.toUsuarioDTO() }
            .singleOrNull()
    }
}