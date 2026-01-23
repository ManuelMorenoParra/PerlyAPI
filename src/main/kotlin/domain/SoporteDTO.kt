package edu.gva.es.domain

import kotlinx.serialization.Serializable

@Serializable
data class SoporteDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val asunto: String,
    val descripcion: String,
    val estado: String = "ABIERTO",
    val respuesta: String? = null,
    val fechaApertura: String,
    val fechaRespuesta: String? = null
)
