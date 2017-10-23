import io.javalin.Javalin

fun main(args: Array<String>) {
    Javalin.create().apply {
        post("/upload") { ctx ->
            ctx.uploadedFiles("files").forEach { (contentType, content, name, ext) ->
                FileUtils.copyInputStreamToFile(content, File("upload/" + name))
            }
        }
    }.start();
}

