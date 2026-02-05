package domain

import kotlinx.serialization.Serializable

@Serializable
data class BloqueoDTO(
    val id: Int? = null,
    val idBloqueador: Int,
    val idBloqueado: Int,
    val fecha: String? = null
)