package ru.practicum.compilations.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
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
    @Size(min = 1, max = 50)
    private final String title;
}
