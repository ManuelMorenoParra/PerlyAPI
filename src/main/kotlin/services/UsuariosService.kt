package edu.gva.es.services

import data.Mensajes
import data.Publicaciones
import edu.gva.es.data.*
import edu.gva.es.domain.UsuarioDTO
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

    fun actualizarUsuario(id: Int, usuario: UsuarioDTO): Int = UsuariosDAO.actualizar(id, usuario)

    fun eliminar(id: Int): Int = UsuariosDAO.eliminar(id)

    fun eliminarUsuarioCompleto(idUsuarioParam: Int) {
        transaction {

            Publicaciones.deleteWhere { Publicaciones.idUsuario eq idUsuarioParam }

            Soportes.deleteWhere { Soportes.idUsuario eq idUsuarioParam }
 
            Likes.deleteWhere { Likes.idUsuario eq idUsuarioParam }

            Mensajes.deleteWhere { (Mensajes.idEmisor eq idUsuarioParam) or (Mensajes.idReceptor eq idUsuarioParam) }

            Seguidores.deleteWhere { (Seguidores.idUsuario eq idUsuarioParam) or (Seguidores.idSeguido eq idUsuarioParam) }

            Usuarios.deleteWhere { Usuarios.idUsuario eq idUsuarioParam }
        }
    }

    fun buscarPorEmail(email: String): UsuarioDTO? = UsuariosDAO.seleccionarPorEmail(email)

    fun login(email: String, pass: String): Boolean {
        val usuario = UsuariosDAO.seleccionarPorEmail(email)
        return usuario != null && usuario.password == pass
    }
}