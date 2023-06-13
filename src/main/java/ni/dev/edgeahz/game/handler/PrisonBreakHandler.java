package ni.dev.edgeahz.game.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ni.dev.edgeahz.game.domain.model.request.PrisonRequest;
import ni.dev.edgeahz.game.exception.PrisonBreakGameException;
import ni.dev.edgeahz.game.service.PrisonBreakService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class PrisonBreakHandler {

    private final Validator validator;

    private final PrisonBreakService prisonBreakService;

    public Mono<ServerResponse> start(ServerRequest request) {
        Mono<PrisonRequest> prisonRequestMono = request.bodyToMono(PrisonRequest.class);

        return prisonRequestMono.flatMap(prisonRequest -> {
            String message = validation(prisonRequest);
            if(message == null) {
                return prisonBreakService.check(prisonRequest.getPrison())
                        .flatMap(canEscape ->
                                ServerResponse.status(canEscape ? HttpStatus.OK : HttpStatus.FORBIDDEN).build()
                        );
            }
            return Mono.error(new PrisonBreakGameException(message));
        }).switchIfEmpty(ServerResponse.status(HttpStatus.FORBIDDEN).build());
    }

    public Mono<ServerResponse> status(ServerRequest request) {

        return prisonBreakService.status()
                .filter(Objects::nonNull)
                .flatMap(prisonBreakReportResponse ->
                        ServerResponse.ok().bodyValue(prisonBreakReportResponse)
                ).onErrorResume(RuntimeException.class, ex -> ServerResponse.status(HttpStatus.NOT_FOUND).build());
    }

    private <T> String validation(T t) {
        Iterator<ConstraintViolation<T>> it = validator.validate(t).iterator();
        String message = null;
        while (it.hasNext()) {
            ConstraintViolation<T> constraintViolation = it.next();
            log.info("Error message : {}", constraintViolation.getMessage());
            message = constraintViolation.getMessage();
        }
        return message;
    }
}
