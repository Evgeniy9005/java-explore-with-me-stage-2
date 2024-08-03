package ru.practicum.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.BadRequestException;
import ru.practicum.ConflictException;
import ru.practicum.NotFoundException;
import ru.practicum.util.Util;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handle(final NotFoundException e) {
        log.debug("Получен статус 404 Not found {}",e.getMessage(),e);
        return ApiError.builder()
                /*.errors(Arrays.stream(e.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString())
                        .collect(Collectors.toList()))*/
                .status(HttpStatus.NOT_FOUND.toString())
                .reason(e.toString())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(Util.getFormatter()))
                .build();
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(final ConstraintViolationException e) {
        log.debug("Получен статус 404 Not found {}",e.getMessage(),e);
        return ApiError.builder()
                /*.errors(Arrays.stream(e.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString())
                        .collect(Collectors.toList()))*/
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
               /* .errors(Arrays.stream(e.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString())
                        .collect(Collectors.toList()))*/
                .status(HttpStatus.BAD_REQUEST.toString())
                .reason(e.toString())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(Util.getFormatter()))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handle(final ConflictException e) {
        log.debug("Получен статус 409 conflict {}",e.getMessage(),e);
        return ApiError.builder()
                /*.errors(Arrays.stream(e.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString())
                        .collect(Collectors.toList()))*/
                .status(HttpStatus.CONFLICT.toString())
                .reason(e.toString())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now().format(Util.getFormatter()))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handle(final DataIntegrityViolationException e) {
        log.debug("Получен статус 409 conflict {}",e.getMessage(),e);
        return ApiError.builder()
                /*.errors(Arrays.stream(e.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString())
                        .collect(Collectors.toList()))*/
                .status(HttpStatus.CONFLICT.toString())
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
                /*.errors(Arrays.stream(e.getStackTrace()).map(stackTraceElement -> stackTraceElement.toString())
                        .collect(Collectors.toList()))*/
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .reason(e.getMessage())
                .message("Произошла непредвиденная ошибка!")
                .timestamp(LocalDateTime.now().format(Util.getFormatter()))
                .build();
    }
}
