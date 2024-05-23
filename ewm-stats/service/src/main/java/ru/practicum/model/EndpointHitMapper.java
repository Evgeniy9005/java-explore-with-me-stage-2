package ru.practicum.model;

import dto.EndpointHitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {


       EndpointHitDto toEndpointHitDto(EndpointHit hit);

       @Mapping(target = "timestamp", expression = "java(parsingTimestamp(hitDto))")
       EndpointHit toEndpointHit(EndpointHitDto hitDto);


       default LocalDateTime parsingTimestamp(EndpointHitDto hitDto) {
              DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
              LocalDateTime timestamp = LocalDateTime.parse(hitDto.getTimestamp(), formatter);
              return timestamp;
       }
}
