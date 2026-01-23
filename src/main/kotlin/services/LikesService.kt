package edu.gva.es.services

import edu.gva.es.data.LikesDAO
import edu.gva.es.domain.LikeDTO

object LikesService {
    fun darLike(dto: LikeDTO) = LikesDAO.insertar(dto)
    fun quitarLike(usuario: Int, pub: Int) = LikesDAO.eliminar(usuario, pub)
    fun contar(pub: Int) = LikesDAO.contarLikes(pub)
}
