package ru.practicum.error;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Data
@RequiredArgsConstructor
@Builder
public class ApiError {
    /**Список стектрейсов или описания ошибок*/
    private final List<String> errors;
    /**Сообщение об ошибке*/
    private final String message;
    /**Общее описание причины ошибки*/
    private final String reason;
    /**Код статуса HTTP-ответа*/
    private final String status;
    /**Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")*/
    private final String timestamp;
}
