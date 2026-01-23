package services

import data.ComentariosDAO
import domain.ComentarioDTO

class ComentariosService {

    private val dao = ComentariosDAO()

    fun getComentariosDePublicacion(idPublicacion: Int) =
        dao.getByPublicacion(idPublicacion)

    fun crearComentario(comentario: ComentarioDTO) =
        dao.insert(comentario)

    fun eliminarComentario(id: Int) =
        dao.delete(id)
}
