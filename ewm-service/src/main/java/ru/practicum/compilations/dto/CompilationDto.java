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
    /**Идентификатор подборки*/
    private final int id;
    /**Список событий входящих в подборку*/
    private final List<EventShortDto> events;
    /**Закреплена ли подборка на главной странице сайта*/
    private final boolean pinned;
    /**Заголовок подборки*/
    private final String title;
}
