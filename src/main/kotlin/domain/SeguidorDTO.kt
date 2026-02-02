package edu.gva.es.domain

import kotlinx.serialization.Serializable

@Serializable
data class SeguidorDTO(
    val id: Int? = null,
    val idUsuario: Int = 0,
    val idSeguido: Int = 0,
    val fecha: String? = null
)