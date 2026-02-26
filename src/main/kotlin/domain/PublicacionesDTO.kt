package edu.gva.es.domain

import kotlinx.serialization.Serializable

@Serializable
data class PublicacionesDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val texto: String,
    val fecha: String? = null,
    val imagen: String? = null,
    val idRetoVinculado: Int? = null,
    val likesCount: Int = 0,
    val likedBy: List<String> = emptyList()
)