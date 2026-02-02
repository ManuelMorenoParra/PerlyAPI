package domain

import kotlinx.serialization.Serializable

@Serializable

data class MensajeDTO(

    val id: Int? = null,
    val idEmisor: Int = 0,
    val idReceptor: Int = 0,
    val mensaje: String,
    val fecha: String? = null,
    val leido: Boolean = false
)

