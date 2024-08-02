package team_alcoholic.jumo_server.global.error.exhandler.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team_alcoholic.jumo_server.global.error.exception.NotFoundException;
import team_alcoholic.jumo_server.global.error.exhandler.ErrorResult;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResult handleNotFoundException(NotFoundException e) {
        return new ErrorResult("404", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult handleException(Exception e) {
        return new ErrorResult("500", e.getMessage());
    }

}