package data

import domain.PublicacionDTO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import java.time.LocalDateTime
import java.util.Base64

object PublicacionesDAO {

    private fun ResultRow.toDTO(): PublicacionDTO {
        // Extraemos el blob y lo convertimos a Base64 si existe
        val blob = this.getOrNull(Publicaciones.imagen)
        val base64String = blob?.let {
            Base64.getEncoder().encodeToString(it.bytes)
        }

        return PublicacionDTO(
            id = this[Publicaciones.id],
            idUsuario = this[Publicaciones.idUsuario],
            texto = this[Publicaciones.texto],
            fecha = this[Publicaciones.fecha].toString(),
            imagenBase64 = base64String
        )
    }

    fun getAll(): List<PublicacionDTO> = transaction {
        Publicaciones.selectAll().map { it.toDTO() }
    }

    fun getByUsuario(idUsuario: Int): List<PublicacionDTO> = transaction {
        Publicaciones.selectAll().where { Publicaciones.idUsuario eq idUsuario }
            .map { it.toDTO() }
    }

    // Unificamos el insert para que acepte el objeto con o sin imagen
    fun insert(pub: PublicacionDTO): Int = transaction {
        Publicaciones.insert {
            it[idUsuario] = pub.idUsuario
            it[texto] = pub.texto
            it[fecha] = LocalDateTime.now()

            // Si el DTO trae Base64, lo convertimos a Bytes para el BLOB
            pub.imagenBase64?.let { base64 ->
                val bytes = Base64.getDecoder().decode(base64)
                it[imagen] = ExposedBlob(bytes)
            }
        } get Publicaciones.id
    }

    fun delete(id: Int): Boolean = transaction {
        Publicaciones.deleteWhere { Publicaciones.id eq id } > 0
    }

    fun actualizar(idPublicacion: Int, dto: PublicacionDTO): Boolean = transaction {
        Publicaciones.update({ Publicaciones.id eq idPublicacion }) {
            it[texto] = dto.texto
            // Actualizamos imagen solo si se envía una nueva
            dto.imagenBase64?.let { base64 ->
                val bytes = Base64.getDecoder().decode(base64)
                it[imagen] = ExposedBlob(bytes)
            }
        } > 0
    }
}