package domain

import kotlinx.serialization.Serializable

@Serializable
data class ComentarioDTO(
    val id: Int? = null,
    val idPublicacion: Int,
    val idUsuario: Int,
    val texto: String,
    val fecha: String? = null
)
