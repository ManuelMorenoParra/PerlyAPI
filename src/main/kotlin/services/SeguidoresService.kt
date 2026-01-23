package edu.gva.es.services

import edu.gva.es.data.SeguidoresDAO
import edu.gva.es.domain.SeguidorDTO

object SeguidoresService {
    fun seguir(dto: SeguidorDTO) = SeguidoresDAO.seguir(dto)
    fun dejar(usuario: Int, seguido: Int) = SeguidoresDAO.dejarDeSeguir(usuario, seguido)
    fun listar(id: Int) = SeguidoresDAO.obtenerSeguidores(id)
}
