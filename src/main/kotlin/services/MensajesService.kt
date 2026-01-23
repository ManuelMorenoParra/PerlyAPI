package services

import data.MensajesDAO
import domain.MensajeDTO

class MensajesService {

    private val dao = MensajesDAO()

    fun getMensajesUsuario(idUsuario: Int) =
        dao.getMensajesDeUsuario(idUsuario)

    fun enviarMensaje(mensaje: MensajeDTO) =
        dao.insert(mensaje)

    fun marcarLeido(id: Int) =
        dao.marcarLeido(id)

    fun eliminarMensaje(id: Int) =
        dao.delete(id)
}
