package ru.practicum.users.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**Заявка на участие в событии*/
@Data
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class ParticipationRequestDto {
    /**Идентификатор заявки*/
    private final int id;
    /**Дата и время создания заявки. example: 2022-09-06T21:10:05.432*/
    private final String created;
    /**Идентификатор события*/
    private final int event;
    /**Идентификатор пользователя, отправившего заявку*/
    private final int requester;
    /**Статус заявки*/
    private final String status;
}
