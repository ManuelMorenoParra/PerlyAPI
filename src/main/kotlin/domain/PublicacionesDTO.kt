package edu.gva.es.domain

import kotlinx.serialization.Serializable
// Borra los imports de Table y datetime si no los usas aquí

@Serializable
data class PublicacionesDTO(
    val id: Int? = null,
    val idUsuario: Int,
    val texto: String,
    val fecha: String, // O LocalDateTime si prefieres
    val imagen: ByteArray? = null // Los blobs se manejan como ByteArray en Kotlin
)