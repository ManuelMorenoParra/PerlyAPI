package edu.gva.es.services

import edu.gva.es.data.BloqueosDAO
import edu.gva.es.domain.BloqueosDTO

class BloqueosService {
    fun obtenerTodos(): List<BloqueosDTO> = BloqueosDAO.getAll()
    fun bloquearUsuario(dto: BloqueosDTO): Int = BloqueosDAO.insert(dto)
    fun desbloquearUsuario(idBloqueador: Int, idBloqueado: Int): Boolean =
        BloqueosDAO.delete(idBloqueador, idBloqueado)
}