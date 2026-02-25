package edu.gva.es.services

import edu.gva.es.data.BloqueosDAO
import edu.gva.es.domain.BloqueoDTO

class BloqueosService {
    fun obtenerTodos(): List<BloqueoDTO> = BloqueosDAO.getAll()
    fun bloquearUsuario(dto: BloqueoDTO): Int = BloqueosDAO.insert(dto)
    fun desbloquearUsuario(idBloqueador: Int, idBloqueado: Int): Boolean =
        BloqueosDAO.delete(idBloqueador, idBloqueado)
}