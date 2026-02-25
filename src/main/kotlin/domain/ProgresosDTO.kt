package edu.gva.es.domain
import kotlinx.serialization.Serializable

@Serializable
data class ProgresosDTO(
    val id: Int? = null, val idUsuario: Int,
    val idReto: Int, val puntosGanados: Int,
    val fecha: String? = null,
    val completado: Boolean = true
)