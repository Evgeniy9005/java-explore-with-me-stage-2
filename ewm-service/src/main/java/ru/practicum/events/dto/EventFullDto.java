package ru.practicum.events.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.constants.State;
import ru.practicum.events.model.Location;
import ru.practicum.users.dto.UserShortDto;

@Data
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class EventFullDto {
    /**Краткое описание*/
    private final String annotation;
    /**Объект типа CategoryDto*/
    private final CategoryDto category;
    /**Количество одобренных заявок на участие в данном событии*/
    private final Integer confirmedRequests;
    /**Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")*/
    private final String createdOn;
    /**Полное описание события*/
    private final String description;
    /**Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")*/
    private final String eventDate;
    /**Идентификатор события*/
    private final Integer id;
    /**Объект типа UserShortDto*/
    private final UserShortDto initiator;
    /**Объект типа Location*/
    private final Location location;
    /**Нужно ли оплачивать участие*/
    private final Boolean paid;
    /**Ограничение на количество участников. Значение 0 - означает отсутствие ограничения*/
    private final Integer participantLimit;
    /**Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")*/
    private final String publishedOn;
    /**Нужна ли пре-модерация заявок на участие*/
    private final Boolean requestModeration;
    /**Список состояний жизненного цикла события "enum": ["PENDING", "PUBLISHED", "CANCELED"]*/
    private final State state;
    /**Заголовок события*/
    private final String title;
    /**Количество просмотрев события*/
    private final Integer views;
}
