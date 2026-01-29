package edu.gva.es.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import edu.gva.es.routes.usuarioRouting
import routes.retosRouting
import routes.publicacionesRouting
import routes.mensajesRouting
import routes.comentariosRouting
import routes.progresoRouting
import routes.likesRouting
import routes.seguidoresRouting
import routes.soporteRouting

fun Application.configureRouting() {

    routing {

        get("/") {
            call.respondText("API Pearly funcionando correctamente")
        }

        usuarioRouting()
        retosRouting()
        publicacionesRouting()
        mensajesRouting()
        comentariosRouting()
        progresoRouting()
        likesRouting()
        seguidoresRouting()
        soporteRouting()
    }
}
