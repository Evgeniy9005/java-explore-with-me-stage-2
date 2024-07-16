package ru.practicum.users.request.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.users.request.ParticipationRequestDto;
import ru.practicum.users.request.model.ParticipationRequest;
import ru.practicum.util.Util;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    @Mapping(target = "created",expression = "java(getConvertDateCreate(pr))")
    @Mapping(target = "event",expression = "java(getIdEvent(pr))")
    @Mapping(target = "requester",expression = "java(getIdRequester(pr))")
    ParticipationRequestDto toDto(ParticipationRequest pr);

    default String getConvertDateCreate(ParticipationRequest pr) {
        return pr.getCreated().format(Util.getFormatter());
    }

    default int getIdEvent(ParticipationRequest pr) {
        return pr.getEvent().getId();
    }

    default int getIdRequester(ParticipationRequest pr) {
        return pr.getRequester().getId();
    }
}
