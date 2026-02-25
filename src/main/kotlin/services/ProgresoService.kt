package edu.gva.es.services

import edu.gva.es.data.ProgresoDAO
import edu.gva.es.domain.ProgresoDTO

class ProgresosService {
    fun obtenerProgresoUsuario(idUsuario: Int): List<ProgresoDTO> =
        ProgresoDAO.obtenerPorUsuario(idUsuario)

    fun registrarProgreso(dto: ProgresoDTO): Int =
        ProgresoDAO.insertar(dto)

    fun obtenerPuntosTotales(idUsuario: Int): Int =
        ProgresoDAO.sumarPuntosUsuario(idUsuario)

    fun editarProgreso(id: Int, dto: ProgresoDTO): Boolean =
        ProgresoDAO.actualizar(id, dto)

    fun eliminarProgreso(id: Int): Boolean =
        ProgresoDAO.eliminar(id)
}