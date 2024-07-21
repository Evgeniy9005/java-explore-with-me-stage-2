package ru.practicum.compilations.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.events.coverter.EventsMapper;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CompilationMapper {
    @Mapping(target = "events",expression = "java(compilationToEventShortDto(events))")
    CompilationDto toCompilationDto(Compilation compilation, List<Event> events);

    default List<EventShortDto> compilationToEventShortDto(List<Event> events) {
        return events.stream()
                .map(event -> Mappers.getMapper(EventsMapper.class).toEventShortDto(event))
                .collect(Collectors.toList());
    }

   /* default List<EventShortDto> compilationToEventShortDto(Compilation compilation) {
        return compilation.getEvents().stream()
                .map(event -> Mappers.getMapper(EventsMapper.class).toEventShortDto(event))
                .collect(Collectors.toList());
    }*/
}
