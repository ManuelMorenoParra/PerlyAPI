package services

import edu.gva.es.data.ComentariosDAO
import domain.ComentarioDTO

class ComentariosService {

    fun getComentariosDePublicacion(idPublicacion: Int) =
        ComentariosDAO.getByPublicacion(idPublicacion)

    fun crearComentario(comentario: ComentarioDTO) =
        ComentariosDAO.insert(comentario)

    fun eliminarComentario(id: Int) =
        ComentariosDAO.delete(id)
}
