package edu.gva.es.services

import edu.gva.es.data.PublicacionesDAO
import edu.gva.es.domain.PublicacionesDTO

object PublicacionesService {

    fun getAll(): List<PublicacionesDTO> = PublicacionesDAO.getAll()

    fun getByUsuario(idUsuario: Int): List<PublicacionesDTO> = PublicacionesDAO.getByUsuario(idUsuario)

    fun create(pub: PublicacionesDTO): Int = PublicacionesDAO.insert(pub)

    fun update(id: Int, pub: PublicacionesDTO): Boolean = PublicacionesDAO.update(id, pub)

    fun delete(id: Int): Boolean = PublicacionesDAO.delete(id)
}