package edu.gva.es.services

import edu.gva.es.data.SoportesDAO
import edu.gva.es.domain.SoportesDTO

object SoportesService {
    fun crearTicket(ticket: SoportesDTO): Int = SoportesDAO.insertar(ticket)

    fun obtenerMisTickets(idUsuario: Int): List<SoportesDTO> = SoportesDAO.listarPorUsuario(idUsuario)

    fun responderTicket(idTicket: Int, respuesta: String) {
        SoportesDAO.actualizarEstado(idTicket, "resolved", respuesta)
    }
}