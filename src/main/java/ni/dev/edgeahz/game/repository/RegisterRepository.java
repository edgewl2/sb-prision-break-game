package ni.dev.edgeahz.game.repository;

import ni.dev.edgeahz.game.domain.entity.Register;
import ni.dev.edgeahz.game.domain.entity.Score;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RegisterRepository extends ReactiveCrudRepository<Register, Long> {

    Mono<Integer> countAllByEscape(Boolean escape);

    @Query(value = "SELECT count(*) AS total," +
            "(SELECT count(*) AS escape_count FROM Register r1 WHERE r1.can_escape = true) AS escape_success," +
            "(SELECT count(*) AS escape_count FROM register r2 WHERE r2.can_escape = false) AS escape_failure FROM Register r")
    Mono<Score> getReportEscape();
}
