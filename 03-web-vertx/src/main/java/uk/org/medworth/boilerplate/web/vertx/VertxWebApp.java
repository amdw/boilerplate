package uk.org.medworth.boilerplate.web.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;

import java.util.Optional;

public class VertxWebApp {
    private static final Logger LOG = LoggerFactory.getLogger(VertxWebApp.class);

    private final ThymeleafTemplateEngine templateEngine = ThymeleafTemplateEngine.create();

    HttpServer prepareServer(Vertx vertx) {
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.route().handler(LoggerHandler.create());
        router.get("/speech").handler(this::speechHandler);
        router.get().handler(StaticHandler.create("static"));

        server.requestHandler(router::accept);
        return server;
    }

    private void run(Vertx vertx, int port) {
        HttpServer server = prepareServer(vertx);
        server.listen(port, event -> {
            if (event.succeeded()) {
                LOG.info("Server successfully started on port " + port);
            } else {
                LOG.fatal("Server could not successfully start on port " + port, event.cause());
                System.exit(-1);
            }
        });
    }

    private void speechHandler(RoutingContext context) {
        String occasion = Optional.ofNullable(context.request().params().get("occasion")).orElse("Hong Kong Code Conf");
        context.put("occasion", occasion);
        templateEngine.render(context, "templates/speech.html", result -> {
            if (result.succeeded()) {
                context.response().putHeader(HttpHeaders.CONTENT_TYPE, "text/html");
                context.response().end(result.result());
            } else {
                context.fail(result.cause());
            }
        });
    }

    public static void main(String[] args) {
        new VertxWebApp().run(Vertx.vertx(), 8080);
    }
}
