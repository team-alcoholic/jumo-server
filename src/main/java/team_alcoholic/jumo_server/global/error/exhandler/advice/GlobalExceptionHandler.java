package team_alcoholic.jumo_server.global.error.exhandler.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team_alcoholic.jumo_server.global.error.exception.NotFoundException;
import team_alcoholic.jumo_server.global.error.exception.UnauthorizedException;
import team_alcoholic.jumo_server.global.error.exhandler.ErrorResult;

import java.nio.file.AccessDeniedException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResult handleNotFoundException(NotFoundException e) {
        return new ErrorResult("404", "Resource not found", List.of(new ErrorResult.ErrorDetail(null, e.getMessage())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResult> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorResult.ErrorDetail> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResult.ErrorDetail(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResult errorResult = new ErrorResult("400", "Validation failed for one or more fields", errorDetails);
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResult> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResult errorResult = new ErrorResult("403", "Access Denied", List.of(new ErrorResult.ErrorDetail(null, e.getMessage())));
        return new ResponseEntity<>(errorResult, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResult> handleUnauthorizedException(UnauthorizedException e) {
        ErrorResult errorResult = new ErrorResult("401", "Unauthorized", List.of(new ErrorResult.ErrorDetail(null, e.getMessage())));
        return new ResponseEntity<>(errorResult, HttpStatus.UNAUTHORIZED);
    }

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public ErrorResult handleException(Exception e) {
//        return new ErrorResult("500", e.getMessage());
//    }

}