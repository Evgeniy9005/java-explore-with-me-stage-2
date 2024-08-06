package ru.practicum.events.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.annotations.DoDateArrived;
import ru.practicum.events.model.Location;

import javax.validation.constraints.*;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class NewEventDto {

    /**Краткое описание события*/
    @NotBlank
    @Size(min = 20, max = 2000)
    private final String annotation;
    /**id категории к которой относится событие*/
    private final int category;
    /**Полное описание события*/
    @NotBlank
    @Size(min = 20, max = 7000)
    private final String description;
    /**Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"*/
    @DoDateArrived
    @NotBlank
    private final String eventDate;
    /**Широта и долгота места проведения события*/
    private final Location location;
    /**Нужно ли оплачивать участие в событии*/
    @Builder.Default
    private final boolean paid = false;
    /**Ограничение на количество участников. Значение 0 - означает отсутствие ограничения*/
    @PositiveOrZero
    @Builder.Default
    private final int participantLimit = 0;
    /**Нужна ли пре-модерация заявок на участие. Если true,
     то все заявки будут ожидать подтверждения инициатором события.
     Если false - то будут подтверждаться автоматически*/
    @Builder.Default
    private final Boolean requestModeration = true;
    /**Заголовок события*/
    @Size(min = 3, max = 120)
    private final String title;
}
