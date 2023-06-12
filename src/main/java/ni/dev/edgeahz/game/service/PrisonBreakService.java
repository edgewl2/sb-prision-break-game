package ni.dev.edgeahz.game.service;

import ni.dev.edgeahz.game.domain.model.response.PrisonBreakReportResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PrisonBreakService {

    Mono<Boolean> check(List<String> prison);

    Mono<PrisonBreakReportResponse> status();

    boolean canEscape(String[] prison);
}
