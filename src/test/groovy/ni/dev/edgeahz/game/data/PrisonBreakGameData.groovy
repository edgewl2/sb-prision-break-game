package ni.dev.edgeahz.game.data

import ni.dev.edgeahz.game.domain.entity.Summary
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpCookie
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.lang.Nullable
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Flux

import java.util.function.Consumer

class PrisonBreakGameData {

    static def canEscapeThePrisonHasExit() {
        return List.of("||||||S||", "|P ||   |", "||  | | |", "|v| | < |",
                "| |   | |", "|   |   |", "|||||||||")
    }

    static def canEscapeThePrisonDoesntHaveExit() {
        return List.of("|||||||||", "|P ||   |", "||  | | |", "|v| | < |",
                "| |   | |", "|   |   |", "|||||||||")
    }

    static def canEscapeThePrisonDoesntHavePrisoner() {
        return List.of("||||||S||", "|S ||   |", "||  | | |", "|v| | < |",
                "| |   | |", "|   |   |", "|||||||||")
    }

    static def summaryOfSavedEscapes() {
        return Summary.builder()
                .successfulEscape(7)
                .failureEscape(12)
                .total(19)
                .build()
    }
}
