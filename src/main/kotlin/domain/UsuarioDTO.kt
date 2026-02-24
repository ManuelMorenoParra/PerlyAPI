package edu.gva.es.domain

import kotlinx.serialization.Serializable

@Serializable
data class UsuarioDTO(

    val id: Int? = null,
    val nombre: String,
    val email: String,
    val password: String? = null,
    val fechaNacimiento: String? = null
)