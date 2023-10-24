package net.wintang.zooapp.advice;

import net.wintang.zooapp.dto.response.ErrorResponseObject;
import net.wintang.zooapp.exception.DuplicatedKeyException;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.exception.PermissionDeniedException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseObject> notFoundHandler(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseObject("", ex.getErrorMessage(), ""));
    }

    @ExceptionHandler(DuplicatedKeyException.class)
    public ResponseEntity<ErrorResponseObject> duplicatedKeyHandler(DuplicatedKeyException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseObject("", ex.getErrorMessage(), ""));
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorResponseObject> permissionDeniedHandler(PermissionDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ErrorResponseObject("", ex.getErrorMessage(), ""));
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponseObject> dataAccessHandler(DataAccessException ex) {
        String message = ex.getMessage();
        if (ex.getMessage().contains("REFERENCE")) {
            message = "You can't delete this because it is associated with other data. Please disable using update instead.";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseObject("", message, ""));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseObject> methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : result.getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseObject("", "", errorMessages));
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<ErrorResponseObject> classCastHandler(ClassCastException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseObject("", "", "Please check your token!"));
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ErrorResponseObject> authCredentialsNotFoundHandler(AuthenticationCredentialsNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseObject("", "", ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseObject> authenticationHandler(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseObject("", "", ex.getMessage()));
    }
}
