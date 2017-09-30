import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;


import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class FunctionalWebApplication {

    static RouterFunction getRouter(){

        HandlerFunction message = serverRequest -> ok().body(fromObject("message"));
        HandlerFunction messageJson = serverRequest -> ok().contentType(APPLICATION_JSON).body(fromObject(new Message("message json")));

        return route(GET("/"),message).andRoute(GET("/json"),messageJson);
    }

    public static void main(String[] args) throws InterruptedException {

        HttpServer.create("localhost",8080).newHandler(new ReactorHttpHandlerAdapter(RouterFunctions.toHttpHandler(getRouter()))).block();
        Thread.currentThread().join();
    }
}
