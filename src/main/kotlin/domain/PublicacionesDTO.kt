package edu.gva.es.domain

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class PublicacionesDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val texto: String,
    val fecha: String? = null,
    val imagen: String? = null, // Ahora es String (Base64)
    val idRetoVinculado: Int? = null // Nuevo atributo
)