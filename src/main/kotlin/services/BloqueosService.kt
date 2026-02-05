package services

import edu.gva.es.data.BloqueosDAO
import domain.BloqueoDTO

class BloqueosService {

    fun bloquearUsuario(dto: BloqueoDTO): Boolean {
        // Si ya está bloqueado, no hacemos nada
        if (BloqueosDAO.estaBloqueado(dto.idBloqueador, dto.idBloqueado)) return false

        return BloqueosDAO.insert(dto) > 0
    }

    fun desbloquearUsuario(idBloqueador: Int, idBloqueado: Int): Boolean {
        return BloqueosDAO.delete(idBloqueador, idBloqueado) > 0
    }
}