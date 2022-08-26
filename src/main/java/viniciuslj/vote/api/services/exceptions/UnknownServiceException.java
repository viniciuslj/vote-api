package viniciuslj.vote.api.services.exceptions;

import viniciuslj.vote.api.Messages;

public class UnknownServiceException extends RuntimeException {
    public UnknownServiceException(String message) {
        super(message);
    }

    public UnknownServiceException() {
        super(Messages.INTERNAL_SERVER_ERROR);
    }
}
