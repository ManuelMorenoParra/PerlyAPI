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

    /**
     * LIMPIEZA TOTAL: Borra al usuario y sus dependencias.
     * Ahora incluye Publicaciones para evitar el error de Foreign Key.
     */
    fun eliminarUsuarioCompleto(idUsuarioParam: Int) {
        transaction {
            // 1. Borrar lo que depende de Publicaciones (si tuvieras Comentarios o Likes de posts, irían aquí)

            // 2. Borrar Publicaciones (El culpable del error 500)
            Publicaciones.deleteWhere { Publicaciones.idUsuario eq idUsuarioParam }

            // 3. Borrar el resto de rastro del usuario
            Soportes.deleteWhere { Soportes.idUsuario eq idUsuarioParam }
            Likes.deleteWhere { Likes.idUsuario eq idUsuarioParam }

            // 4. Borrar Mensajes (emisor o receptor)
            Mensajes.deleteWhere { (Mensajes.idEmisor eq idUsuarioParam) or (Mensajes.idReceptor eq idUsuarioParam) }

            // 5. Borrar Seguidores (seguidor o seguido)
            Seguidores.deleteWhere { (Seguidores.idUsuario eq idUsuarioParam) or (Seguidores.idSeguido eq idUsuarioParam) }

            // 6. Finalmente, borrar al Usuario
            Usuarios.deleteWhere { Usuarios.idUsuario eq idUsuarioParam }
        }
    }

    fun buscarPorEmail(email: String): UsuarioDTO? = UsuariosDAO.seleccionarPorEmail(email)

    fun login(email: String, pass: String): Boolean {
        val usuario = UsuariosDAO.seleccionarPorEmail(email)
        return usuario != null && usuario.password == pass
    }
}