package edu.gva.es.domain

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class LikeDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val idPublicacion: Int,
    val fecha: String
)
