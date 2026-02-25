package edu.gva.es.services

import edu.gva.es.data.SoportesDAO
import edu.gva.es.domain.SoportesDTO

object SoportesService {
    fun crear(dto: SoportesDTO): Int = SoportesDAO.crear(dto)

    fun responder(id: Int, r: String) = SoportesDAO.responder(id, r)

    fun listarPorUsuario(idUsuario: Int): List<SoportesDTO> = SoportesDAO.listarPorUsuario(idUsuario)

    fun editarSoporte(id: Int, dto: SoportesDTO): Boolean {
        return SoportesDAO.actualizar(id, dto)
    }

    fun eliminar(id: Int): Boolean = SoportesDAO.eliminar(id)
}