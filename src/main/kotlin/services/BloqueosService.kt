package services

import edu.gva.es.data.BloqueosDAO
import domain.BloqueoDTO

class BloqueosService {
    // Esta es la función que te falta o que tiene otro nombre
    fun obtenerTodos(): List<BloqueoDTO> {
        return BloqueosDAO.getAll()
    }

    fun bloquearUsuario(dto: BloqueoDTO): Int = BloqueosDAO.insert(dto)

    fun desbloquearUsuario(idBloqueador: Int, idBloqueado: Int): Boolean =
        BloqueosDAO.delete(idBloqueador, idBloqueado)
}