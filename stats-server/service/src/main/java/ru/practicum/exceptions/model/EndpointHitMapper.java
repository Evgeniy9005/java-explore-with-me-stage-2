package ru.practicum.exceptions.model;

import dto.EndpointHitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface EndpointHitMapper {
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

       @Mapping(target = "timestamp", expression = "java(formatDateTime(hit))")
       EndpointHitDto toEndpointHitDto(EndpointHit hit);

       @Mapping(target = "timestamp", expression = "java(parsingTimestamp(hitDto))")
       EndpointHit toEndpointHit(EndpointHitDto hitDto);


       default LocalDateTime parsingTimestamp(EndpointHitDto hitDto) {
          LocalDateTime timestamp = LocalDateTime.parse(hitDto.getTimestamp(), formatter);
          return timestamp;
       }

       default String formatDateTime(EndpointHit hit) {
              return hit.getTimestamp().format(formatter);
       }
}
