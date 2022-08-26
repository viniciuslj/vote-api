package viniciuslj.vote.api.controllers.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.Instant;

@Getter
public class StandardErrorResponse {
    private final Instant timestamp;
    private final Integer status;
    private final String error;
    private final String message;
    private final String path;

    public StandardErrorResponse(HttpStatus httpStatus, String message, String path) {
        this.timestamp = Instant.now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}
