package edu.gva.es.services

import data.MensajesDAO
import domain.MensajeDTO
import edu.gva.es.data.BloqueosDAO

class MensajesService {

    private val dao = MensajesDAO()

    fun getMensajesUsuario(idUsuario: Int) =
        dao.getMensajesDeUsuario(idUsuario)

    fun marcarLeido(id: Int) =
        dao.marcarLeido(id)

    fun eliminarMensaje(id: Int) =
        dao.delete(id)

    fun actualizarMensaje(id: Int, dto: MensajeDTO): Boolean {
        val filasAfectadas = dao.update(id, dto)
        return filasAfectadas > 0
    }

    fun enviarMensaje(mensaje: MensajeDTO): Int {

        if (BloqueosDAO.estaBloqueado(mensaje.idReceptor, mensaje.idEmisor)) {
            throw IllegalStateException("No puedes enviar mensajes: este usuario te ha bloqueado")
        }

        return dao.insert(mensaje)
    }
}