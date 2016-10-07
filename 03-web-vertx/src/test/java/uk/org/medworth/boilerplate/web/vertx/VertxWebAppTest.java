package uk.org.medworth.boilerplate.web.vertx;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.ServerSocket;

@RunWith(VertxUnitRunner.class)
public class VertxWebAppTest {
    private Vertx vertx;
    private int port;

    @Before
    public void setup(TestContext context) throws IOException {
        vertx = Vertx.vertx();
        ServerSocket socket = new ServerSocket(0);
        port = socket.getLocalPort();
        socket.close();
        VertxWebApp app = new VertxWebApp();
        HttpServer server = app.prepareServer(vertx);
        server.listen(port, context.asyncAssertSuccess());
    }

    @After
    public void teardown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void testStaticRoute(TestContext context) {
        Async async = context.async();
        vertx.createHttpClient().getNow(port, "localhost", "/",
                assertSuccessfulResponse(context, "text/html;charset=UTF-8", "Listen to a speech", async));
    }

    @Test
    public void testTemplateRouteDefault(TestContext context) {
        Async async = context.async();
        vertx.createHttpClient().getNow(port, "localhost", "/speech",
                assertSuccessfulResponse(context, "text/html", "Hong Kong Code Conf", async));
    }

    @Test
    public void testTemplateRouteWithParam(TestContext context) {
        Async async = context.async();
        String occasion = "JavaOne";
        vertx.createHttpClient().getNow(port, "localhost", "/speech?occasion=" + occasion,
                assertSuccessfulResponse(context, "text/html", occasion, async));
    }

    private Handler<HttpClientResponse> assertSuccessfulResponse(TestContext context, String contentType,
                                                                 String content, Async async) {
        return response -> response.handler(body -> {
            context.assertEquals(200, response.statusCode());
            context.assertEquals(contentType, response.headers().get(HttpHeaders.CONTENT_TYPE));
            context.assertTrue(body.toString().contains(content));
            async.complete();
        });
    }
}
