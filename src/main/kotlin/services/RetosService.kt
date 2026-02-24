package edu.gva.es.services

import data.RetosDAO
import domain.RetoDTO

object RetosService {

    fun getAllRetos(): List<RetoDTO> = RetosDAO.obtenerTodos()

    fun getRetoById(id: Int): RetoDTO? = RetosDAO.obtenerPorId(id)

    fun createReto(reto: RetoDTO): RetoDTO = RetosDAO.crearReto(reto)

    fun updateReto(id: Int, reto: RetoDTO): Boolean {

        val retoConId = reto.copy(id = id)
        return RetosDAO.actualizarReto(retoConId)
    }

    fun deleteReto(id: Int): Boolean = RetosDAO.eliminarReto(id)
}
