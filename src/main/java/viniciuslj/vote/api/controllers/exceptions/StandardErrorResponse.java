package viniciuslj.vote.api.controllers.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.Instant;

@Getter
public class StandardErrorResponse {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardErrorResponse(HttpStatus httpStatus, String message, String path) {
        this.timestamp = Instant.now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}
