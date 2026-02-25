package edu.gva.es.domain

import kotlinx.serialization.Serializable

@Serializable
data class SoportesDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val asunto: String,
    val descripcion: String,
    val respuesta: String? = null,
    val fechaApertura: String? = null,
    val fechaRespuesta: String? = null,
)