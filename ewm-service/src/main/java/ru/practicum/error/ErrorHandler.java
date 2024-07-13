package ru.practicum.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.BadRequestException;
import ru.practicum.NotFoundException;
import ru.practicum.util.Util;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handle(final NotFoundException e) {
        log.debug("Получен статус 404 Not found {}",e.getMessage(),e);
        return ApiError.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .reason(e.toString())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(Util.getFormatter()))
                .build();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(final BadRequestException e) {
        log.debug("Получен статус 400 Bad request {}",e.getMessage(),e);
        return ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason(e.toString())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(Util.getFormatter()))
                .build();
    }



    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handle(final RuntimeException e) {
        e.printStackTrace();
        log.debug("Получен статус 500 internal server error {}",e.getMessage(),e);
        return ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .reason(e.getMessage())
                .message("Произошла непредвиденная ошибка!")
                .timestamp(LocalDateTime.now().format(Util.getFormatter()))
                .build();
    }
}
