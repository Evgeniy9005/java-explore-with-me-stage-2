package ru.practicum.events.coverter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.category.converter.CategoryMapper;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.users.converter.UserMapper;
import ru.practicum.users.dto.UserShortDto;
import ru.practicum.util.Util;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring")
public interface EventsMapper {

   // Event toEvent(EventFullDto eventFullDto);

    @Mapping(target = "category",expression = "java(categoryToCategoryDto(event))")
    @Mapping(target = "initiator",expression = "java(userToUserShortDto(event))")
    @Mapping(target = "eventDate",expression = "java(getConvertDateEventDate(event))")
    EventShortDto toEventShortDto(Event event);



    @Mapping(target = "category",expression = "java(categoryToCategoryDto(event))")
    @Mapping(target = "initiator",expression = "java(userToUserShortDto(event))")
    @Mapping(target = "location",expression = "java(toLocation(event))")
    @Mapping(target = "createdOn",expression = "java(getConvertDateCreateOn(event))")
    @Mapping(target = "eventDate",expression = "java(getConvertDateEventDate(event))")
    @Mapping(target = "publishedOn",expression = "java(getConvertDatePublishedOn(event))")
    EventFullDto toEventFullDto(Event event);

    /*Задействует CategoryMapper для преобразования Category в CategoryDto*/
    default CategoryDto categoryToCategoryDto(Event event) {
        return Mappers.getMapper(CategoryMapper.class).toCategoryDto(event.getCategory());
    }

    /*Задействует UserMapper для преобразования User в UserShortDto*/
    default UserShortDto userToUserShortDto(Event event) {
        return Mappers.getMapper(UserMapper.class).toUserShortDto(event.getInitiator());
    }

    default Location toLocation(Event event) {
        return new Location(event.getLat(),event.getLon());
    }

    /*Преобразование даты создания события в строку для DTO*/
    default String getConvertDateCreateOn(Event event) {
        return event.getCreatedOn().format(Util.getFormatter());
    }

    /*Преобразование даты начала события в строку для DTO*/
    default String getConvertDateEventDate(Event event) {
        return event.getEventDate().format(Util.getFormatter());
    }

    /*Преобразование даты публикации события в строку для DTO*/
    default String getConvertDatePublishedOn(Event event) {
        LocalDateTime publishedOn = event.getPublishedOn();
        if(publishedOn == null) {
            return "";
        }
        return publishedOn.format(Util.getFormatter());
    }
}
