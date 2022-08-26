package viniciuslj.vote.api.controllers.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import viniciuslj.vote.api.Messages;
import viniciuslj.vote.api.services.exceptions.BusinessLogicException;
import viniciuslj.vote.api.services.exceptions.EntityNotFoundException;
import viniciuslj.vote.api.services.exceptions.UnknownServiceException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<StandardErrorResponse> handleEntityNotFound(
            EntityNotFoundException exception,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StandardErrorResponse(
                        HttpStatus.NOT_FOUND,
                        exception.getMessage(),
                        request.getRequestURI())
                );
    }

    @ExceptionHandler
    public ResponseEntity<StandardErrorResponse> handleBusinessLogic(
            BusinessLogicException exception,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StandardErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        exception.getMessage(),
                        request.getRequestURI())
                );
    }

    @ExceptionHandler
    public ResponseEntity<StandardErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StandardErrorResponse(
                        HttpStatus.BAD_REQUEST,
                        joinAllDefaultErrors(exception.getBindingResult().getAllErrors()),
                        request.getRequestURI())
                );
    }

    private String joinAllDefaultErrors(List<ObjectError> objectErrorList) {
        List<String> errors = objectErrorList.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return String.join(", ", errors);
    }

    @ExceptionHandler({UnknownServiceException.class, Exception.class})
    public ResponseEntity<StandardErrorResponse> handleUnknownService(
            Exception exception,
            HttpServletRequest request) {

        log.error(exception.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StandardErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        Messages.INTERNAL_SERVER_ERROR,
                        request.getRequestURI())
                );
    }
}
