package edu.gva.es.services

import edu.gva.es.data.SoporteDAO
import edu.gva.es.domain.SoporteDTO

object SoporteService {
    fun crear(dto: SoporteDTO) = SoporteDAO.crear(dto)
    fun responder(id: Int, r: String) = SoporteDAO.responder(id, r)
    fun listar(id: Int) = SoporteDAO.listarPorUsuario(id)
}
