package ru.practicum.events.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.events.model.Location;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class NewEventDto {

    /**
     Краткое описание события
     */
    @NotBlank
    @Max(2000)
    @Min(20)
    private final String annotation;

    /*annotation*	string
    maxLength: 2000
    minLength: 20
    example: Сплав на байдарках похож на полет.
    */
    /**
     id категории к которой относится событие
     */
    private final int category;
   /* category*	integer($int64)
    example: 2
    */

    /**
     Полное описание события
     */
    @Max(7000)
    @Min(20)
    private final String description;
    /*description*	string
    maxLength: 7000
    minLength: 20
    example: Сплав на байдарках похож на полет. На спокойной воде — это парение. На бурной, порожистой — выполнение фигур высшего пилотажа. И то, и другое дарят чувство обновления, феерические эмоции, яркие впечатления.
    Полное описание события*/
    /**
     Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
     */
    private final String eventDate;
    /*eventDate*	string
    example: 2024-12-31 15:10:05
*/
    /**
     Широта и долгота места проведения события
     */
    private final Location location;
    /*location*	Location{...}
    Jump to path*/
    /**
     Нужно ли оплачивать участие в событии
     */
    @Builder.Default
    private final boolean paid = false;
    /*paid	boolean
    example: true
    default: false
    */
    /**
     Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
     */
    @Builder.Default
    private final int participantLimit = 0;
   /* participantLimit	integer($int32)
    example: 10
    default: 0
    */
    /**
     Нужна ли пре-модерация заявок на участие. Если true,
     то все заявки будут ожидать подтверждения инициатором события.
     Если false - то будут подтверждаться автоматически
     */
    @Builder.Default
    private final boolean requestModeration = true;
    /*requestModeration	boolean
    example: false
    default: true
    */
    /**
     Заголовок события
     */
    @Max(120)
    @Min(3)
    private final String title;
    /*title*	string
    maxLength: 120
    minLength: 3
    example: Сплав на байдарках
   */

}
