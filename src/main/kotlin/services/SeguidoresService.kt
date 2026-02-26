package edu.gva.es.services

import edu.gva.es.data.SeguidoresDAO

object SeguidoresService {
    fun seguirUsuario(seguidor: Int, seguido: Int) = SeguidoresDAO.seguir(seguidor, seguido)

    fun dejarDeSeguir(seguidor: Int, seguido: Int) = SeguidoresDAO.dejarDeSeguir(seguidor, seguido)

    fun obtenerEstadisticas(idUsuario: Int) = mapOf(
        "followers" to SeguidoresDAO.contarSeguidores(idUsuario),
        "following" to SeguidoresDAO.contarSeguidos(idUsuario)
    )

    fun verificarRelacion(seguidor: Int, seguido: Int) = SeguidoresDAO.esSeguidor(seguidor, seguido)
}