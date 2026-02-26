package edu.gva.es.domain

import kotlinx.serialization.Serializable

@Serializable
data class SeguidoresDTO(
    val idSeguimiento: Int? = null,
    val idSeguidor: Int, // Coincide con tu script SQL
    val idSeguido: Int,  // Coincide con tu script SQL
    val fechaSeguimiento: String? = null
)