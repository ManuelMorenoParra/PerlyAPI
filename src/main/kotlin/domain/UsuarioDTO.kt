package edu.gva.es.domain

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class UsuarioDTO(
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    @Serializable(with = LocalDateSerializer::class)
    val fechaNacimiento: LocalDate
)
