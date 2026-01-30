package services

import data.PublicacionesDAO
import domain.PublicacionDTO

class PublicacionesService {

    fun getAll(): List<PublicacionDTO> =
        PublicacionesDAO.getAll()

    fun getByUsuario(idUsuario: Int): List<PublicacionDTO> =
        PublicacionesDAO.getByUsuario(idUsuario)

    fun create(pub: PublicacionDTO): Int =
        PublicacionesDAO.insert(pub)

    fun delete(id: Int): Boolean =
        PublicacionesDAO.delete(id)
}
