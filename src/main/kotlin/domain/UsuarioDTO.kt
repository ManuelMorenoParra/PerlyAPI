package edu.gva.es.domain
import kotlinx.serialization.Serializable

@Serializable
data class UsuariosDTO(
    val id: Int? = null,
    val nombre: String,
    val email: String,
    val password: String? = null
)