package edu.gva.es.services

import edu.gva.es.data.SoportesDAO
import edu.gva.es.domain.SoporteDTO

object SoportesService { // Añadida la 's' para coincidir con la ruta
    fun crear(dto: SoporteDTO) = SoportesDAO.crear(dto)

    fun responder(id: Int, r: String) = SoportesDAO.responder(id, r)

    fun listarPorUsuario(idUsuario: Int): List<SoporteDTO> = SoportesDAO.listarPorUsuario(idUsuario)

    fun editarSoporte(id: Int, dto: SoporteDTO): Boolean {
        return SoportesDAO.actualizar(id, dto)
    }

    // Método que pedía la ruta delete("/{id}")
    fun eliminar(id: Int): Boolean {
        return SoportesDAO.eliminar(id)
    }
}