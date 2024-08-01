package ru.practicum.compilations.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

/**Изменение информации о подборке событий. Если поле в запросе не указано (равно null)
 - значит изменение этих данных не требуется.*/
@Data
@RequiredArgsConstructor
public class UpdateCompilationRequest {
    /**Список id событий подборки для полной замены текущего списка*/
    private final List<Integer> events;
    /**Закреплена ли подборка на главной странице сайта*/
    private final Boolean pinned;
    /**Заголовок подборки*/
    @Size(min = 1, max = 50)
    private final String title;
}

