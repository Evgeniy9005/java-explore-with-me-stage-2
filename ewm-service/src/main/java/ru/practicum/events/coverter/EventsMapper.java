package ru.practicum.events.coverter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.category.converter.CategoryMapper;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.users.converter.UserMapper;
import ru.practicum.users.dto.UserDto;

@Mapper(componentModel = "spring")
public interface EventsMapper {

    Event toEvent(EventFullDto eventFullDto);

    @Mapping(target = "category",expression = "java(categoryToCategoryDto(event))")
    @Mapping(target = "initiator",expression = "java(userToUserDto(event))")
    @Mapping(target = "location",expression = "java(toLocation(event))")
    EventFullDto toEventFullDto(Event event);

    default CategoryDto categoryToCategoryDto(Event event) {
        return Mappers.getMapper(CategoryMapper.class).toCategoryDto(event.getCategory());
    }

    default UserDto userToUserDto(Event event) {
        return Mappers.getMapper(UserMapper.class).toUserDto(event.getInitiator());
    }

    default Location toLocation(Event event) {
        return new Location(event.getLat(),event.getLon());
    }
}
