package viniciuslj.vote.api.services.exceptions;

public class UnknownServiceException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "Internal Server Error";

    public UnknownServiceException(String message) {
        super(message);
    }

    public UnknownServiceException() {
        super(DEFAULT_MESSAGE);
    }
}
