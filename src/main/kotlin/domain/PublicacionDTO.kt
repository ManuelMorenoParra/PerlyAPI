package domain

import kotlinx.serialization.Serializable

@Serializable
data class PublicacionDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val texto: String,
    val fecha: String? = null
)
