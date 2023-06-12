package ni.dev.edgeahz.game.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.WebProperties;

@Configuration
@Order(-2)
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {

    public ExceptionHandler(ErrorAttributes errorAttributes, WebProperties resources,
                            ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources.getResources(), applicationContext);
        super.setMessageReaders(configurer.getReaders());
        super.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderError);
    }

    private Mono<ServerResponse> renderError(ServerRequest serverRequest) {
        Map<String, Object> error = this.getErrorAttributes(serverRequest, ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.MESSAGE,
                ErrorAttributeOptions.Include.BINDING_ERRORS,
                ErrorAttributeOptions.Include.EXCEPTION,
                ErrorAttributeOptions.Include.STACK_TRACE));
        error.remove("requestId");

        if(String.valueOf(error.get("path")).equalsIgnoreCase("/prisoner")) {
            error.remove("status");
            error.remove("trace");
            error.remove("timestamp");
            error.remove("error");
            error.remove("exception");
        }
        return ServerResponse.status(HttpStatus.BAD_REQUEST).body(BodyInserters.fromValue(error));
    }
}
