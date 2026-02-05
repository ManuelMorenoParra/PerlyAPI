package edu.gva.es.plugins

import edu.gva.es.routes.seguidoresRouting
import edu.gva.es.routes.usuarioRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import routes.*

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
        bloqueosRouting()
    }
}
