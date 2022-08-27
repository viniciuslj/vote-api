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
import viniciuslj.vote.api.services.exceptions.UnauthorizedException;
import viniciuslj.vote.api.services.exceptions.UnknownServiceException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

    private ResponseEntity<StandardErrorResponse> makeResponse(
            HttpStatus httpStatus, String message, HttpServletRequest request) {

        return ResponseEntity.status(httpStatus)
                .body(new StandardErrorResponse(
                        httpStatus,
                        message,
                        request.getRequestURI())
                );
    }

    @ExceptionHandler
    public ResponseEntity<StandardErrorResponse> handleEntityNotFound(
            EntityNotFoundException exception,
            HttpServletRequest request) {
        return makeResponse(HttpStatus.NOT_FOUND, exception.getMessage(), request);
    }

    @ExceptionHandler
    public ResponseEntity<StandardErrorResponse> handleBusinessLogic(
            BusinessLogicException exception,
            HttpServletRequest request) {
        return makeResponse(HttpStatus.BAD_REQUEST, exception.getMessage(), request);
    }

    @ExceptionHandler
    public ResponseEntity<StandardErrorResponse> handleUnauthorized(
            UnauthorizedException exception,
            HttpServletRequest request) {
        return makeResponse(HttpStatus.UNAUTHORIZED, exception.getMessage(), request);
    }

    @ExceptionHandler
    public ResponseEntity<StandardErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {
        String message = joinAllDefaultErrors(exception.getBindingResult().getAllErrors());
        return makeResponse(HttpStatus.BAD_REQUEST, message, request);
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

        return makeResponse(HttpStatus.INTERNAL_SERVER_ERROR, Messages.INTERNAL_SERVER_ERROR, request);
    }
}
