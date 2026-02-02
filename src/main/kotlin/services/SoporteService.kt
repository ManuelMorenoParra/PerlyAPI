package edu.gva.es.services

import edu.gva.es.data.SoportesDAO
import edu.gva.es.domain.SoporteDTO

object SoporteService {
    fun crear(dto: SoporteDTO) = SoportesDAO.crear(dto)
    fun responder(id: Int, r: String) = SoportesDAO.responder(id, r)
    fun listar(id: Int) = SoportesDAO.listarPorUsuario(id)
}
