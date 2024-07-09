package ru.practicum.users.dto.request;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.events.constants.State;

import java.util.List;

/**Изменение статуса запроса на участие в событии текущего пользователя*/
@Data
@RequiredArgsConstructor
public class EventRequestStatusUpdateRequest {

    /**Идентификаторы запросов на участие в событии текущего пользователя*/
    private final List<Integer> requestIds;
    /**Новый статус запроса на участие в событии текущего пользователя*/
    private final State status;

}
