package ru.practicum.users.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.annotations.DoDateArrived;
import ru.practicum.annotations.DoHeBlank;
import ru.practicum.events.model.Location;

import javax.validation.constraints.*;


/**Данные для изменения информации о событии. Если поле в запросе не указано (равно null)
- значит изменение этих данных не требуется.*/
@Data
@RequiredArgsConstructor
public class UpdateEventUserRequest {
    /**Новая аннотация*/
    @DoHeBlank(message = "Аннотация не должна быть пустая!")
    @Size(min = 20, max = 2000)
    private final String annotation;
    /**Новая категория*/
    private final Integer category;
    /**Новое описание*/
    @DoHeBlank(message = "Описание не должна быть пустым!")
    @Size(min = 20, max = 7000)
    private final String description;
    /**Новые дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"*/
    @DoDateArrived
    private final String eventDate;
    /**Широта и долгота места проведения события*/
    private final Location location;
    /**Новое значение флага о платности мероприятия*/
    private final Boolean paid;
    /**Новый лимит пользователей*/
    @PositiveOrZero
    private final Integer participantLimit;
    /**Нужна ли пре-модерация заявок на участие*/
    private final Boolean requestModeration;
    /**Изменение состояния события*/
    private final String stateAction;
    /**Новый заголовок*/
    @Size(min = 3, max = 120)
    private final String title;
}
