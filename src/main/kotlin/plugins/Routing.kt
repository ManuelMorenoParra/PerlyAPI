package edu.gva.es.plugins

import edu.gva.es.routes.* // Importamos todo desde el paquete correcto
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {

        get("/") {
            call.respondText("API Pearly funcionando correctamente")
        }

        usuarioRouting()
        retosRouting()
        publicacionesRouting()
        comentariosRouting()
        progresoRouting()
        likesRouting()
        seguidoresRouting()
        soporteRouting()
        bloqueosRouting()
    }
}