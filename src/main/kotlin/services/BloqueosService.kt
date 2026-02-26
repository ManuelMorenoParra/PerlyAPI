package edu.gva.es.services

import edu.gva.es.data.BloqueosDAO
import edu.gva.es.domain.BloqueosDTO

object BloqueosService {

    fun bloquearUsuario(dto: BloqueosDTO): Int {
        return BloqueosDAO.bloquear(dto)
    }

    fun listarBloqueadosPorUsuario(idUsuario: Int): List<BloqueosDTO> {
        return BloqueosDAO.obtenerBloqueadosPorUsuario(idUsuario)
    }

    fun eliminarBloqueo(idBloqueador: Int, idBloqueado: Int, tipo: String): Boolean {
        return BloqueosDAO.eliminarBloqueo(idBloqueador, idBloqueado, tipo)
    }

    // Útil para filtrar el Feed: ¿Este usuario ha bloqueado al autor de la publicación?
    fun existeRestriccion(idBloqueador: Int, idBloqueado: Int): Boolean {
        val lista = BloqueosDAO.obtenerBloqueadosPorUsuario(idBloqueador)
        return lista.any { it.idBloqueado == idBloqueado }
    }
}