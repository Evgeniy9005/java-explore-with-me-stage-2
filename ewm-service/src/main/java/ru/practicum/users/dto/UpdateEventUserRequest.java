package ru.practicum.users.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.events.model.Location;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


/**Данные для изменения информации о событии. Если поле в запросе не указано (равно null)
- значит изменение этих данных не требуется.*/
@Data
@RequiredArgsConstructor
public class UpdateEventUserRequest {
    /**Новая аннотация*/
    @Max(2000)
    @Min(20)
    private final String annotation;
    /**Новая категория*/
    private final Integer category;
    /**Новое описание*/
    @Max(7000)
    @Min(20)
    private final String description;
    /**Новые дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"*/
    private final String eventDate;
    /**Широта и долгота места проведения события*/
    private final Location location;
    /**Новое значение флага о платности мероприятия*/
    private final Boolean paid;
    /**Новый лимит пользователей*/
    private final Integer participantLimit;
    /**Нужна ли пре-модерация заявок на участие*/
    private final Boolean requestModeration;
    /**Изменение состояния события*/
    private final String stateAction;
    /**Новый заголовок*/
    private final String title;
}
