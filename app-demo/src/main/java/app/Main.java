package app;

import app.config.ThymeleafConfig;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import app.controllers.UserController;

public class Main {
    public static void main(String[] args) {
        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        // Routing
        app.get("/", ctx -> ctx.render("/index.html"));
        //app.post("/login", ctx -> ctx.redirect("/welcome.html"));

        app.post("/login", ctx ->
        {
            UserController.login(ctx);
        });

        app.get("/welcome.html", ctx -> ctx.render("/welcome.html"));
    }
}
