package edu.gva.es.services

import edu.gva.es.data.SoportesDAO
import edu.gva.es.domain.SoporteDTO

object SoportesService {
    fun crear(dto: SoporteDTO): Int = SoportesDAO.crear(dto)

    fun responder(id: Int, r: String) = SoportesDAO.responder(id, r)

    fun listarPorUsuario(idUsuario: Int): List<SoporteDTO> = SoportesDAO.listarPorUsuario(idUsuario)

    fun editarSoporte(id: Int, dto: SoporteDTO): Boolean {
        return SoportesDAO.actualizar(id, dto)
    }

    fun eliminar(id: Int): Boolean = SoportesDAO.eliminar(id)
}