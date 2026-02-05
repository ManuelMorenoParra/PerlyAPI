package services

import data.MensajesDAO
import domain.MensajeDTO
import edu.gva.es.data.BloqueosDAO

class MensajesService {

    private val dao = MensajesDAO()

    fun getMensajesUsuario(idUsuario: Int) =
        dao.getMensajesDeUsuario(idUsuario)

    fun enviarMensaje(mensaje: MensajeDTO): Int {
        // 1. Comprobamos si el RECEPTOR tiene bloqueado al EMISOR
        // Si el receptor bloqueó al emisor, el emisor no puede enviar mensajes.
        if (BloqueosDAO.estaBloqueado(mensaje.idReceptor, mensaje.idEmisor)) {
            // Lanzamos una excepción que capturaremos en la Ruta para devolver un 403 Forbidden
            throw IllegalStateException("No puedes enviar mensajes a este usuario porque te ha bloqueado")
        }

        // 2. Si no hay bloqueo, procedemos al insert
        return dao.insert(mensaje)
    }

    fun marcarLeido(id: Int) =
        dao.marcarLeido(id)

    fun eliminarMensaje(id: Int) =
        dao.delete(id)

    fun actualizarMensaje(id: Int, dto: MensajeDTO): Boolean {
        val filasAfectadas = dao.update(id, dto)
        return filasAfectadas > 0
    }
}