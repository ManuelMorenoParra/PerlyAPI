package services

import data.ProgresoDAO
import domain.ProgresoDTO

class ProgresoService {

    private val dao = ProgresoDAO()

    fun obtenerProgresoUsuario(idUsuario: Int) = dao.getByUsuario(idUsuario)

    fun registrarProgreso(progreso: ProgresoDTO): Int {
        if (dao.yaCompletado(progreso.idUsuario, progreso.idReto)) {
            throw Exception("Este reto ya fue completado por el usuario")
        }
        return dao.insert(progreso)
    }

    fun obtenerPuntosTotales(idUsuario: Int) = dao.totalPuntosUsuario(idUsuario)

    fun eliminarProgreso(id: Int): Boolean {
        return dao.delete(id) > 0
    }

    // Esta es la función limpia que llama al DAO
    fun editarProgreso(id: Int, dto: ProgresoDTO): Boolean {
        return dao.actualizar(id, dto)
    }
}