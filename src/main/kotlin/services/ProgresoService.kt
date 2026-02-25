package edu.gva.es.services

import edu.gva.es.data.ProgresosDAO
import edu.gva.es.domain.ProgresosDTO

class ProgresosService {
    fun obtenerProgresoUsuario(idUsuario: Int): List<ProgresosDTO> =
        ProgresosDAO.getByUsuario(idUsuario)

    fun registrarProgreso(dto: ProgresosDTO): Int =
        ProgresosDAO.insert(dto)

    fun obtenerPuntosTotales(idUsuario: Int): Int =
        ProgresosDAO.totalPuntosUsuario(idUsuario)

    fun editarProgreso(id: Int, dto: ProgresosDTO): Boolean =
        ProgresosDAO.actualizar(id, dto)

    fun eliminarProgreso(id: Int): Boolean =
        ProgresosDAO.delete(id)
}