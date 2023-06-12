package ni.dev.edgeahz.game.routes;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import ni.dev.edgeahz.game.domain.model.request.PrisonRequest;
import ni.dev.edgeahz.game.handler.PrisonBreakHandler;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Slf4j
@Configuration
public class PrisonBreakRouterConfig {

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/prisoner",
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.POST,
                    beanClass = PrisonBreakHandler.class,
                    beanMethod = "start",
                    operation = @Operation(
                            operationId = "start",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful Operation - Escape way found."
                                    ),
                                    @ApiResponse(
                                            responseCode = "403",
                                            description = "Forbidden Operation - Escape way not found."
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "BadRequest Operation - Prison information broken."
                                    )
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(
                                            implementation = PrisonRequest.class
                                    ))
                            )
                    )
            ),
            @RouterOperation(
                    path = "/stats",
                    produces = {
                            MediaType.APPLICATION_JSON_VALUE
                    },
                    method = RequestMethod.GET,
                    beanClass = PrisonBreakHandler.class,
                    beanMethod = "status",
                    operation = @Operation(
                            operationId = "status",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Successful Operation - Get scores information."
                                    ),
                                    @ApiResponse(
                                            responseCode = "404",
                                            description = "NotFound Operation - Scores not found."
                                    )
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> routes(PrisonBreakHandler handler) {
        return RouterFunctions.route(POST("/prisoner").and(accept(MediaType.APPLICATION_JSON)), handler::start)
                .andRoute(GET("/stats").and(accept(MediaType.ALL)), handler::status);
    }

}
