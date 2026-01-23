package services

import data.RetosDAO
import domain.RetoDTO

class RetosService {

    private val dao = RetosDAO()

    fun getAllRetos(): List<RetoDTO> = dao.getAll()

    fun getRetoById(id: Int): RetoDTO? = dao.getById(id)

    fun createReto(reto: RetoDTO): Int = dao.insert(reto)

    fun updateReto(id: Int, reto: RetoDTO): Boolean = dao.update(id, reto)

    fun deleteReto(id: Int): Boolean = dao.delete(id)
}
