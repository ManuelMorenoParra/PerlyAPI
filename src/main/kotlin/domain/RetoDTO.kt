package domain

import kotlinx.serialization.Serializable

@Serializable
data class RetoDTO(
    val id: Int? = null,
    val titulo: String,
    val descripcion: String,
    val puntos: Int
)
