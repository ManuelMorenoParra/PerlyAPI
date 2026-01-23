package domain

import kotlinx.serialization.Serializable

@Serializable
data class PublicacionDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val contenido: String,
    val fecha: String? = null
)
