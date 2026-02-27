package edu.gva.es.services

import edu.gva.es.data.PublicacionesDAO
import edu.gva.es.data.BloqueosDAO
import edu.gva.es.domain.PublicacionesDTO

object PublicacionesService {
    
    // getAll ahora puede recibir el ID del usuario que solicita para filtrar
    fun getAll(idUsuarioConsulta: Int? = null): List<PublicacionesDTO> {
        val todas = PublicacionesDAO.getAll()
        
        if (idUsuarioConsulta == null) return todas

        // Obtenemos a quién ha bloqueado o silenciado este usuario
        val restricciones = BloqueosDAO.obtenerBloqueadosPorUsuario(idUsuarioConsulta)
        val idsRestringidos = restricciones.map { it.idBloqueado }

        // Filtramos para que no aparezcan posts de usuarios restringidos
        return todas.filter { it.idUsuario !in idsRestringidos }
    }

    // El resto de funciones se mantienen invocando al DAO
    fun getByUsuario(idUsuario: Int): List<PublicacionesDTO> = PublicacionesDAO.getByUsuario(idUsuario)
    fun create(pub: PublicacionesDTO): Int = PublicacionesDAO.insert(pub)
    fun delete(id: Int): Boolean = PublicacionesDAO.delete(id)
}