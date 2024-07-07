package ru.practicum.compilations.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.events.dto.EventShortDto;

import java.util.List;

@Data
@Builder(toBuilder = true)
@RequiredArgsConstructor
public class CompilationDto {
    /**Список событий входящих в подборку*/
    private final List<EventShortDto> events;
    /**Идентификатор подборки*/
    private final Integer id;
    /**Закреплена ли подборка на главной странице сайта*/
    private final boolean pinned;
    /**Заголовок подборки*/
    private final String title;
}
