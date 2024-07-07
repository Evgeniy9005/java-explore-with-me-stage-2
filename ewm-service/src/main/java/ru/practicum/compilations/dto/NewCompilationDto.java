package ru.practicum.compilations.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.Max;
import java.util.List;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class NewCompilationDto {
    /**Список идентификаторов событий входящих в подборку*/
    private final List<Integer> events;
    /**Закреплена ли подборка на главной странице сайта*/
    @Builder.Default
    private final boolean pinned = false;
    /**Заголовок подборки*/
    @Max(50)
    @Max(1)
    private final String title;
}
