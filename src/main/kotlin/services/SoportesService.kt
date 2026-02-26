package edu.gva.es.services

import edu.gva.es.data.SoportesDAO
import edu.gva.es.domain.SoportesDTO

object SoportesService {
    fun crearTicket(dto: SoportesDTO) = SoportesDAO.insertar(dto)

    fun obtenerMisTickets(idUsuario: Int) = SoportesDAO.listarPorUsuario(idUsuario)

    fun responderTicket(idTicket: Int, respuesta: String) =
        SoportesDAO.actualizarEstado(idTicket, "resolved", respuesta)
}