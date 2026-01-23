package domain

import kotlinx.serialization.Serializable

@Serializable
data class ProgresoDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val idReto: Int,
    val puntosGanados: Int,
    val fecha: String? = null,
    val completado: Boolean = true
)
