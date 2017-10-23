import io.javalin.Javalin

fun main(args: Array<String>) {
    Javalin.create().apply {
        before { ctx ->
            // This runs before every request
        }
        get("/path") { ctx ->
            // This handles GET requests to /path
        }
        after("/path") { ctx ->
            // This runs after every request to /path
        }
    }.start();
}

