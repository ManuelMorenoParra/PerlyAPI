package edu.gva.es.services

import edu.gva.es.data.PublicacionesDAO
import edu.gva.es.domain.PublicacionDTO

class PublicacionesService {

    fun getAll(): List<PublicacionDTO> = PublicacionesDAO.getAll()

    // Este método lo tenías en el DAO pero faltaba en el Service anterior
    fun getByUsuario(idUsuario: Int): List<PublicacionDTO> = PublicacionesDAO.getByUsuario(idUsuario)

    fun create(pub: PublicacionDTO): Int = PublicacionesDAO.insert(pub)

    fun delete(id: Int): Boolean = PublicacionesDAO.delete(id)

    fun editarPublicacion(id: Int, dto: PublicacionDTO): Boolean {
        // Asegúrate de que PublicacionesDAO tenga el método actualizar corregido con Publicaciones.id
        return PublicacionesDAO.actualizar(id, dto)
    }
}