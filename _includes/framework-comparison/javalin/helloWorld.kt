import io.javalin.Javalin

fun main(args: Array<String>) {
    Javalin.create().apply {
        get("/") { ctx -> ctx.result("Hello World") }
    }.start();
}

