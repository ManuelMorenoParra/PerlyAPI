package edu.gva.es.services

import edu.gva.es.data.RetosDAO
import edu.gva.es.domain.RetosDTO

object RetosService {
    fun getAllRetos(): List<RetosDTO> = RetosDAO.obtenerTodos()

    fun getRetoById(id: Int): RetosDTO? = RetosDAO.obtenerPorId(id)

    fun createReto(dto: RetosDTO): Int = RetosDAO.insertar(dto)

    fun updateReto(id: Int, dto: RetosDTO): Boolean = RetosDAO.actualizar(id, dto)

    fun deleteReto(id: Int): Boolean = RetosDAO.eliminar(id)
}