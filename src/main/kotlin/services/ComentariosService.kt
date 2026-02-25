package edu.gva.es.services

import edu.gva.es.data.ComentariosDAO
import edu.gva.es.domain.ComentariosDTO

class ComentariosService {

    fun getComentariosDePublicacion(idPublicacion: Int): List<ComentariosDTO> =
        ComentariosDAO.obtenerPorPublicacion(idPublicacion)

    fun crearComentario(comentario: ComentariosDTO): Int =
        ComentariosDAO.insertar(comentario)

    fun eliminarComentario(id: Int): Boolean =
        ComentariosDAO.eliminar(id)

    fun actualizarComentario(id: Int, comentario: ComentariosDTO): Boolean {

        return ComentariosDAO.actualizar(id, comentario)
    }
}