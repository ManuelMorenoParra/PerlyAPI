package edu.gva.es.domain

import kotlinx.serialization.Serializable

@Serializable
data class BloqueosDTO(
    val id: Int? = null,
    val idBloqueador: Int,
    val idBloqueado: Int,
    val tipo: String = "block", // 'block' o 'mute'
    val fecha: String? = null
)