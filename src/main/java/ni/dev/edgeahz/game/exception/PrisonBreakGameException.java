package ni.dev.edgeahz.game.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class PrisonBreakGameException extends RuntimeException {

    private final HttpStatus status;

    public PrisonBreakGameException() {
        this.status = HttpStatus.BAD_REQUEST;
    }

    public PrisonBreakGameException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public PrisonBreakGameException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public PrisonBreakGameException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
