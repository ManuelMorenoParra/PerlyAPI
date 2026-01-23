package services

import data.PublicacionesDAO
import domain.PublicacionDTO

class PublicacionesService {

    private val dao = PublicacionesDAO()

    fun getAll() = dao.getAll()

    fun create(pub: PublicacionDTO) = dao.insert(pub)

    fun delete(id: Int) = dao.delete(id)
}
