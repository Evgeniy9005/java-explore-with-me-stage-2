package ru.practicum.users.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.constants.State;
import ru.practicum.constants.StatusRequest;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**Изменение статуса запроса на участие в событии текущего пользователя*/
@Data
@RequiredArgsConstructor
public class EventRequestStatusUpdateRequest {

    /**Идентификаторы запросов на участие в событии текущего пользователя*/
    @NotEmpty(message = "Не указан или пустой список заявок на участие в событии!")
    private final List<Integer> requestIds;
    /**Новый статус запроса на участие в событии текущего пользователя*/
    private final StatusRequest status;

}
