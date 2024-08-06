package ru.practicum.compilations.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class UpdateCompilationRequest {
    /**Список id событий подборки для полной замены текущего списка*/
    private final List<Integer> events = new ArrayList<>();
    /**Закреплена ли подборка на главной странице сайта*/
    private final Boolean pinned;
    /**Заголовок подборки*/
    @Size(min = 1, max = 50)
    private final String title;
}

