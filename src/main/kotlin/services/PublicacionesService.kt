package edu.gva.es.services

import edu.gva.es.data.PublicacionesDAO
import edu.gva.es.domain.PublicacionesDTO

class PublicacionesService {

    fun getAll(): List<PublicacionesDTO> = PublicacionesDAO.getAll()

    // Este método lo tenías en el DAO pero faltaba en el Service anterior
    fun getByUsuario(idUsuario: Int): List<PublicacionesDTO> = PublicacionesDAO.getByUsuario(idUsuario)

    fun create(pub: PublicacionesDTO): Int = PublicacionesDAO.insert(pub)

    fun delete(id: Int): Boolean = PublicacionesDAO.delete(id)

    fun editarPublicacion(id: Int, dto: PublicacionesDTO): Boolean {
        // Asegúrate de que PublicacionesDAO tenga el método actualizar corregido con Publicaciones.id
        return PublicacionesDAO.actualizar(id, dto)
    }
}