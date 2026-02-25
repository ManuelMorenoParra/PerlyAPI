package edu.gva.es.domain

import kotlinx.serialization.Serializable

@Serializable
data class ComentariosDTO(
    val id: Int? = null,
    val idPublicacion: Int,
    val idUsuario: Int,
    val contenido: String
)