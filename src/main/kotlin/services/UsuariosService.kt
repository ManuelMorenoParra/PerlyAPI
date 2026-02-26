package edu.gva.es.services

import edu.gva.es.data.*
import edu.gva.es.domain.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.or

object UsuariosService {
    fun listarUsuarios(): List<UsuariosDTO> = UsuariosDAO.seleccionarTodos()

    fun buscarPorId(id: Int): UsuariosDTO? = UsuariosDAO.seleccionarPorId(id)

    fun registrarUsuario(usuario: UsuariosDTO): Int {
        val existe = UsuariosDAO.seleccionarPorEmail(usuario.email) != null
        if (existe) return -1
        return UsuariosDAO.insertar(usuario)
    }

    fun actualizarPerfil(id: Int, datos: UsuariosDTO): Boolean {
        return UsuariosDAO.actualizar(id, datos) > 0
    }

    fun eliminarUsuarioCompleto(idUsuarioParam: Int) = transaction {

        Publicaciones.deleteWhere { Publicaciones.idUsuario eq idUsuarioParam }
        Soportes.deleteWhere { Soportes.idUsuario eq idUsuarioParam }
        Likes.deleteWhere { Likes.idUsuario eq idUsuarioParam }
        Seguidores.deleteWhere { (Seguidores.idSeguidor eq idUsuarioParam) or (Seguidores.idSeguido eq idUsuarioParam) }
        Usuarios.deleteWhere { Usuarios.id eq idUsuarioParam }
    }

    fun login(email: String, pass: String): UsuariosDTO? {
        val usuario = UsuariosDAO.seleccionarPorEmail(email)
        return if (usuario != null && usuario.password == pass) usuario else null
    }
}