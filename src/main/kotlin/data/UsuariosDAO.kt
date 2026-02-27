package edu.gva.es.data

import edu.gva.es.domain.UsuariosDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

object UsuariosDAO {

    private fun ResultRow.toDTO() = UsuariosDTO(
        id = this[Usuarios.id],
        nombre = this[Usuarios.nombre],
        email = this[Usuarios.email],
        password = this[Usuarios.password],
        bio = this[Usuarios.bio],
        avatar = this[Usuarios.avatar],
        achievements = this[Usuarios.achievements],
        puntosEnergia = this[Usuarios.puntosEnergia],
        rachaActual = this[Usuarios.rachaActual],
        isPrivate = this[Usuarios.isPrivate],
        onlyFollowersMessages = this[Usuarios.onlyFollowersMessages]
    )

fun verificarPassword(email: String, passwordPlana: String): UsuariosDTO? = transaction {
    val userRow = Usuarios.selectAll().where { Usuarios.email eq email }.singleOrNull()
    
    if (userRow != null) {
        val hashEnDb = userRow[Usuarios.password]
        // BCrypt.checkpw(passwordPlana, hashDesdeDB)
        if (BCrypt.checkpw(passwordPlana, hashEnDb)) {
            return@transaction userRow.toDTO()
        }
    }
    null
}

    fun seleccionarTodos(): List<UsuariosDTO> = transaction {
        Usuarios.selectAll().map { it.toDTO() }
    }

    fun seleccionarPorId(id: Int): UsuariosDTO? = transaction {
        Usuarios.selectAll().where { Usuarios.id eq id }.map { it.toDTO() }.singleOrNull()
    }

    fun seleccionarPorEmail(email: String): UsuariosDTO? = transaction {
        Usuarios.selectAll().where { Usuarios.email eq email }.map { it.toDTO() }.singleOrNull()
    }

    fun buscarPorNombre(query: String): List<UsuariosDTO> = transaction {
        Usuarios.selectAll()
            .where { Usuarios.nombre.lowerCase() like "%${query.lowercase()}%" }
            .map { it.toDTO() }
    }

 fun insertar(u: UsuariosDTO): Int = transaction {
    Usuarios.insert {
        it[nombre] = u.nombre
        it[email] = u.email
        // IMPORTANTE: Solo hashear si u.password no es nulo
        it[password] = BCrypt.hashpw(u.password ?: throw IllegalArgumentException("Password requerida"), BCrypt.gensalt())
        it[bio] = u.bio
        it[avatar] = u.avatar
        it[achievements] = u.achievements
        it[puntosEnergia] = u.puntosEnergia
        it[rachaActual] = u.rachaActual
        it[isPrivate] = u.isPrivate
        it[onlyFollowersMessages] = u.onlyFollowersMessages
    } get Usuarios.id
}

    fun actualizar(idUsuario: Int, u: UsuariosDTO): Int = transaction {
        Usuarios.update({ Usuarios.id eq idUsuario }) {
            it[nombre] = u.nombre
            it[email] = u.email
            if (u.password != null) {
                it[password] = BCrypt.hashpw(u.password, BCrypt.gensalt())
            }
            it[bio] = u.bio
            it[avatar] = u.avatar
            it[achievements] = u.achievements
            it[puntosEnergia] = u.puntosEnergia
            it[rachaActual] = u.rachaActual
            it[isPrivate] = u.isPrivate
            it[onlyFollowersMessages] = u.onlyFollowersMessages
        }
    }

    fun eliminar(idUsuario: Int): Int = transaction {
        Usuarios.deleteWhere { id eq idUsuario }
    }
}