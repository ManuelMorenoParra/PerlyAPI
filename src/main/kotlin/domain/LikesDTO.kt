package edu.gva.es.domain

import kotlinx.serialization.Serializable

@Serializable

data class LikesDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val idPublicacion: Int
)