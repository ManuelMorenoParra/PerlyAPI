package edu.gva.es.domain
import kotlinx.serialization.Serializable

@Serializable
data class UsuariosDTO(
    val id: Int? = null,
    val nombre: String,
    val email: String,
    val password: String? = null,
    val bio: String? = null,
    val avatar: String? = null,
    val puntosTotales: Int = 0,
    val puntosEnergia: Int = 0,
    val rachaActual: Int = 0
)