package edu.gva.es.services

import edu.gva.es.data.RetosDAO
import edu.gva.es.domain.RetoDTO

object RetosService {
    fun getAllRetos(): List<RetoDTO> = RetosDAO.obtenerTodos()

    fun getRetoById(id: Int): RetoDTO? = RetosDAO.obtenerPorId(id)

    fun createReto(dto: RetoDTO): Int = RetosDAO.insertar(dto)

    fun updateReto(id: Int, dto: RetoDTO): Boolean = RetosDAO.actualizar(id, dto)

    fun deleteReto(id: Int): Boolean = RetosDAO.eliminar(id)
}