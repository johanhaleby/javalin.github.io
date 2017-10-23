import io.javalin.Javalin

fun main(args: Array<String>) {
    Javalin.create().apply {
        enableStaticFiles("/public")
    }.start();
}

