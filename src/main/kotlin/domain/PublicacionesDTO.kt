package edu.gva.es.domain

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class PublicacionDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val texto: String,
    val fecha: String? = null,
    val imagen: String? = null,
    val idRetoVinculado: Int? = null,
    val likesCount: Int = 0,
    val likedBy: List<String> = emptyList() // Lista de nombres de usuario que le dieron like
)