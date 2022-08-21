package viniciuslj.vote.api.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import viniciuslj.vote.api.services.exceptions.BusinessLogicException;
import viniciuslj.vote.api.services.exceptions.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
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
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        return String.join(", ", errors);
    }
}
