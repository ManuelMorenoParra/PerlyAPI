package edu.gva.es.services

import edu.gva.es.data.UsuariosDAO
import edu.gva.es.domain.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.or

object UsuariosService {
    fun listarUsuarios(): List<UsuarioDTO> = UsuariosDAO.seleccionarTodos()
    fun buscarPorId(id: Int): UsuarioDTO? = UsuariosDAO.seleccionarPorId(id)

    fun registrarUsuario(usuario: UsuarioDTO): Int {
        val existe = UsuariosDAO.seleccionarPorEmail(usuario.email) != null
        if (existe) return -1
        return UsuariosDAO.insertar(usuario)
    }

    fun eliminarUsuarioCompleto(idUsuarioParam: Int) = transaction {
        // Usamos los nombres de tablas tal cual los tienes en edu.gva.es.domain
        Publicaciones.deleteWhere { Publicaciones.idUsuario eq idUsuarioParam }
        Soportes.deleteWhere { Soportes.idUsuario eq idUsuarioParam }
        Likes.deleteWhere { Likes.idUsuario eq idUsuarioParam }
        Seguidores.deleteWhere { (Seguidores.idUsuario eq idUsuarioParam) or (Seguidores.idSeguido eq idUsuarioParam) }
        Usuarios.deleteWhere { Usuarios.id eq idUsuarioParam }
    }

    fun login(email: String, pass: String): Boolean {
        val usuario = UsuariosDAO.seleccionarPorEmail(email)
        return usuario != null && usuario.password == pass
    }
}