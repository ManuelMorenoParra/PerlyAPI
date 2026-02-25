package edu.gva.es.services

import edu.gva.es.data.LikesDAO
import edu.gva.es.domain.LikeDTO

object LikesService {
    fun darLike(dto: LikeDTO): Boolean = LikesDAO.insertar(dto)

    fun quitarLike(usuario: Int, pub: Int): Boolean = LikesDAO.eliminar(usuario, pub)

    // Nombre corregido para que la ruta lo encuentre
    fun contarPorPublicacion(pub: Int): Long = LikesDAO.contarLikes(pub)

    fun actualizarLike(id: Int, dto: LikeDTO): Boolean {
        return LikesDAO.update(id, dto)
    }
}