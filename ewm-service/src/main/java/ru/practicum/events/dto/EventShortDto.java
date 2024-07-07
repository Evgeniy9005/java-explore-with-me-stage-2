package ru.practicum.events.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.users.dto.UserShortDto;

@Data
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class EventShortDto {
    /**Краткое описание*/
    private final String annotation;
    /**Объект категории типа CategoryDto*/
    private final CategoryDto category;
    /**Количество одобренных заявок на участие в данном событии*/
    private final Integer confirmedRequests;
    /** Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")*/
    private final String eventDate;
    /**Идентификатор*/
    private final Integer id;
    /**Инициатор Пользователь (краткая информация). Объект типа UserShortDto*/
    private final UserShortDto initiator;
    /**Нужно ли оплачивать участие */
    private final Boolean paid;
    /**Заголовок*/
    private final String title;
    /**Количество просмотрев события*/
    private final Integer views;
}
