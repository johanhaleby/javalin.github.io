import io.javalin.Javalin

fun main(args: Array<String>) {
    Javalin.create().apply {
        ws("/websocket") { ws ->
            ws.onMessage { session, message ->
                session.remote.sendString("Echo: " + message)
            }
        }
    }.start();
}
