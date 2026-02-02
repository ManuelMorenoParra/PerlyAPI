package services // Asegúrate de que el package coincida con tu proyecto

import data.ProgresoDAO
import domain.ProgresoDTO

class ProgresoService {

    private val dao = ProgresoDAO()

    fun obtenerProgresoUsuario(idUsuario: Int) =
        dao.getByUsuario(idUsuario)

    fun registrarProgreso(progreso: ProgresoDTO): Int {
        if (dao.yaCompletado(progreso.idUsuario, progreso.idReto)) {
            throw Exception("Este reto ya fue completado por el usuario")
        }
        return dao.insert(progreso)
    }

    fun obtenerPuntosTotales(idUsuario: Int) =
        dao.totalPuntosUsuario(idUsuario)

    fun delete(id: Int): Boolean {

        val filasBorradas = dao.delete(id)
        return filasBorradas > 0
    }

    fun eliminarProgreso(id: Int): Boolean {

        val filasBorradas = dao.delete(id)
        return filasBorradas > 0
    }
}