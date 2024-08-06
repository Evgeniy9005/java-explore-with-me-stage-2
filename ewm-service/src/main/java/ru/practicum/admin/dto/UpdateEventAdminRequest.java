package ru.practicum.admin.dto;


import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.annotations.DoDateArrived;
import ru.practicum.events.model.Location;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;


/**Данные для изменения информации о событии. Если поле в запросе не указано (равно null)
- значит изменение этих данных не требуется.*/
@Data
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class UpdateEventAdminRequest {

    /**Новая аннотация*/
    @Size(min = 20, max = 2000)
    private final String annotation;
    /**Новая категория*/
    private final Integer category;
    /**Новое описание*/
    @Size(min = 20, max = 7000)
    private final String description;
    /**Новые дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss*/
    @DoDateArrived
    private final String eventDate;
    /**Широта и долгота места проведения события*/
    private final Location location;
    /**Новое значение флага о платности мероприятия*/
    private final Boolean paid;
    /**Новый лимит пользователей*/
    private final Integer participantLimit;
    /**Нужна ли пре-модерация заявок на участие*/
    private final Boolean requestModeration;
    /**Новое состояние события*/
    private final String stateAction;
    /**Новый заголовок*/
    @Size(min = 3,max = 120)
    private final String title;
}
