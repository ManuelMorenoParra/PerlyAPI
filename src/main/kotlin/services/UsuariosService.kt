package edu.gva.es.services

import edu.gva.es.data.UsuariosDAO
import edu.gva.es.domain.UsuarioDTO

/**
 * Capa de Servicio: Aquí reside la lógica de negocio.
 * Se encarga de validar datos y coordinar las llamadas al DAO.
 */
object UsuariosService {

    /**
     * Obtiene todos los usuarios.
     */
    fun listarUsuarios(): List<UsuarioDTO> {
        return UsuariosDAO.seleccionarTodos()
    }

    /**
     * Busca un usuario por ID.
     */
    fun buscarPorId(id: Int): UsuarioDTO? {
        return UsuariosDAO.seleccionarPorId(id)
    }

    /**
     * Lógica para crear un usuario.
     * Se verifica que no exista ya un usuario con el mismo email.
     */
    fun registrarUsuario(usuario: UsuarioDTO): Int {

        val existe = UsuariosDAO.seleccionarTodos().any { it.email == usuario.email }

        if (existe) return -1

        return UsuariosDAO.insertar(usuario)
    }

    /**
     * Actualiza un usuario existente.
     */
    fun actualizarUsuario(id: Int, usuario: UsuarioDTO): Int {
        return UsuariosDAO.actualizar(id, usuario)
    }

    /**
     * Elimina un usuario.
     */
    fun borrarUsuario(id: Int): Int {
        return UsuariosDAO.eliminar(id)
    }

    /**
     * Obtiene un usuario por su email.
     */
    fun obtenerPorEmail(email: String): UsuarioDTO? {
        return UsuariosDAO.buscarPorEmail(email)
    }

    /**
     * Lógica básica de login.
     */
    fun login(email: String, password: String): Boolean {
        val usuario = UsuariosDAO.buscarPorEmail(email)
        return usuario?.password == password
    }
}
