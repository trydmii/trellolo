package ua.trydmi.trellolo.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.trydmi.trellolo.exception.*;

import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {UsernameAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorMessage> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException exception) {
        ErrorMessage customErrorMessage = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(customErrorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {NotAuthenticatedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorMessage> handleInvalidPasswordException(NotAuthenticatedException exception) {
        ErrorMessage customErrorMessage = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(customErrorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }

    @ExceptionHandler(value = {RefreshTokenExpiredException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleRefreshTokenExpiredException(RefreshTokenExpiredException exception) {
        ErrorMessage customErrorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(customErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TokenExpiredException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleTokenExpiredException(TokenExpiredException exception) {
        ErrorMessage customErrorMessage = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(customErrorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {javax.persistence.EntityNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(javax.persistence.EntityNotFoundException exception) {
        ErrorMessage customErrorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                "Entity not found"
        );

        return new ResponseEntity<>(customErrorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {FileAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> handleFileAlreadyExistsException(FileAlreadyExistsException exception) {
        ErrorMessage customErrorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(customErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleUserNotFoundException(UserNotFoundException exception) {
        ErrorMessage customErrorMessage = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(customErrorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {FileDownloadException.class})
    public ResponseEntity<ErrorMessage> handleFileDownloadException(FileDownloadException exception) {
        ErrorMessage customErrorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage()
        );

        return new ResponseEntity<>(customErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

