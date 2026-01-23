package edu.gva.es.domain

import kotlinx.serialization.Serializable

@Serializable
data class SeguidorDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val idSeguido: Int,
    val fecha: String
)
