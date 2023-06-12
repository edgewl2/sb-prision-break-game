package ni.dev.edgeahz.game.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ni.dev.edgeahz.game.constants.Constants;
import ni.dev.edgeahz.game.domain.entity.Register;
import ni.dev.edgeahz.game.domain.model.Position;
import ni.dev.edgeahz.game.domain.model.response.PrisonBreakReportResponse;
import ni.dev.edgeahz.game.repository.RegisterRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrisonBreakServiceImpl implements PrisonBreakService {

    private final RegisterRepository registerRepository;

    @Override
    public Mono<Boolean> check(List<String> prison) {

        Register register = Register.builder()
                .prison(String.join(Constants.DELIMITER, prison))
                .escape(canEscape(prison.toArray(String[]::new)))
                .build();

        return registerRepository.save(register)
                .flatMap(rgt -> Mono.justOrEmpty(register.getEscape()));
    }

    @Override
    public Mono<PrisonBreakReportResponse> status() {

        return registerRepository.getReportEscape()
                .flatMap(score -> Mono.just(PrisonBreakReportResponse.builder()
                                .countSuccessfulEscape(score.getSuccessfulEscape())
                                .countUnsuccessfulEscape(score.getFailureEscape())
                                .ratio(BigDecimal.valueOf((float) score.getSuccessfulEscape() / score.getTotal())
                                        .setScale(1, RoundingMode.HALF_EVEN)
                                        .floatValue())
                                .build()));
    }

    @Override
    public boolean canEscape(String[] prison) {

        int rows = prison.length;
        int cols = prison[0].length();
        boolean[][] reviewedSites = new boolean[rows][cols];

        Position[] directions = {
                new Position(-1, 0), // Arriba
                new Position(1, 0),  // Abajo
                new Position(0, -1), // Izquierda
                new Position(0, 1)   // Derecha
        };

        Position prisonerPosition = findPosition(prison, Constants.PRISONER);
        Position exitPosition = findPosition(prison, Constants.EXIT);

        if (prisonerPosition.getX() == -1 && prisonerPosition.getY() == -1) {
            return false;
        }

        return findEscapeWay(prisonerPosition, exitPosition, directions, prison, reviewedSites);
    }

    private Position findPosition(String[] prison, char target) {
        Position position = Position.builder()
                .x(-1)
                .y(-1)
                .build();

        for (int i = 0; i < prison.length; i++) {
            int col = prison[i].indexOf(target);
            if (col >= 0) {
                position.setX(i);
                position.setY(col);
                return position;
            }
        }

        return position;
    }

    private boolean findEscapeWay(Position currentPosition, Position exitPosition, Position[] directions, String[] prison, boolean[][] reviewedSites) {
        int rows = prison.length;
        int cols = prison[0].length();
        char currentSite = prison[currentPosition.getX()].charAt(currentPosition.getY());

        if (currentPosition.equals(exitPosition)) {
            return true;
        }

        if (currentPosition.getX() < 0 || currentPosition.getX() >= rows
                || currentPosition.getY() < 0 || currentPosition.getY() >= cols
                || reviewedSites[currentPosition.getX()][currentPosition.getY()]
                || Constants.OBSTRUCTIONS.contains(Character.toString(currentSite))
        ) {
            return false;
        }

        reviewedSites[currentPosition.getX()][currentPosition.getY()] = true;

        for (Position direction : directions) {

            Position nextPosition = Position.builder()
                    .x(currentPosition.getX() + direction.getX())
                    .y(currentPosition.getY() + direction.getY())
                    .build();

            if (direction.getX() == 1
                    && hasGuardsObstruct(new Position(currentPosition.getX() + 1, currentPosition.getY()), directions[0], prison)  //Derecha
                    || direction.getX() == -1
                    && hasGuardsObstruct(new Position(currentPosition.getX() - 1, currentPosition.getY()), directions[1], prison)  //Izquierda
                    || direction.getY() == 1
                    && hasGuardsObstruct(new Position(currentPosition.getX(), currentPosition.getY() + 1), directions[2], prison) //Arriba
                    || direction.getY() == -1
                    && hasGuardsObstruct(new Position(currentPosition.getX(), currentPosition.getY() - 1), directions[3], prison)) { //Abajo
                continue;
            }

            if (findEscapeWay(nextPosition, exitPosition, directions, prison, reviewedSites)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasGuardsObstruct(Position guardPosition, Position direction, String[] prison) {
        int rows = prison.length;
        int cols = prison[0].length();

        while (guardPosition.getX() >= 0 && guardPosition.getX() < rows
                && guardPosition.getY() >= 0 && guardPosition.getY() < cols) {

            char currentSite = prison[guardPosition.getX()].charAt(guardPosition.getY());

            if (currentSite == Constants.WALL) {
                return false;
            }

            if (currentSite == Constants.GUARD_RIGHT || currentSite == Constants.GUARD_LEFT
                    || currentSite == Constants.GUARD_UP || currentSite == Constants.GUARD_DOWN) {
                return true;
            }

            guardPosition.setX(guardPosition.getX() + direction.getX());
            guardPosition.setY(guardPosition.getY() + direction.getY());
        }

        return false;
    }

}
