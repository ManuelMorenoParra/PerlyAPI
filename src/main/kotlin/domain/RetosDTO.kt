package edu.gva.es.domain
import kotlinx.serialization.Serializable

@Serializable
data class RetosDTO(
    val id: Int? = null,
    val titulo: String,
    val descripcion: String,
    val categoria: String, // Nueva
    val puntos: Int,
    val esDiario: Boolean = false // Nueva
)