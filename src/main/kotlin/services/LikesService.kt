package edu.gva.es.services

import edu.gva.es.data.LikesDAO
import edu.gva.es.domain.LikeDTO

object LikesService {
    fun darLike(dto: LikeDTO) = LikesDAO.insertar(dto)

    // Asegúrate de que este nombre sea 'quitarLike' y no 'eliminar'
    fun quitarLike(usuario: Int, pub: Int) = LikesDAO.eliminar(usuario, pub)

    fun contar(pub: Int) = LikesDAO.contarLikes(pub)

    // Esta es la que te marcaba error en el PUT
    fun actualizarLike(id: Int, dto: LikeDTO): Boolean {
        return LikesDAO.update(id, dto)
    }
}