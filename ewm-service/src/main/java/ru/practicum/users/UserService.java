package ru.practicum.users;

import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.users.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.users.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.users.dto.request.ParticipationRequestDto;
import ru.practicum.users.model.UpdateEventUserRequest;

import java.util.List;

public interface UserService {
    /**Получение событий, добавленных текущим пользователем*/
    List<EventShortDto> getEventsAddedCurrentUser(String userId, Integer from, Integer size);
    /**Добавление нового события пользователем*/
    EventFullDto addEventUser(NewEventDto newEventDto, String userId);
    /**Получение полной информации о событии добавленном текущим пользователем*/
    EventFullDto getFullInfoAboutEventAddedByCurrentUser(Integer userId, Integer eventId);
    /**Изменение события добавленного текущим пользователем*/
    UpdateEventUserRequest upEventAddedByCurrentUser(Integer userId, Integer eventId);
   /**Получение информации о запросах на участие в событии текущего пользователя*/
    List<ParticipationRequestDto> getInformationRequestsToParticipateCurrentUserEvent(Integer userId,
                                                                                      Integer eventId
    );
    /**Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя*/
    EventRequestStatusUpdateResult upStatusApplicationsParticipationEventCurrentUser(
            EventRequestStatusUpdateRequest updateRequest,
            Integer userId,
            Integer eventId
    );
    /**Получение информации о заявках текущего пользователя на участие в чужих событиях*/
    List<ParticipationRequestDto> getInfoCurrentUserRequestsParticipateOtherPeopleEvents(Integer userId);
    /**Добавление запроса от текущего пользователя на участие в событии*/
    ParticipationRequestDto addRequestCurrentUserParticipateEvent(Integer userId, Integer eventId);
    /**Отмена своего запроса на участие в событии*/
    ParticipationRequestDto upEventToParticipateCancel (Integer userId, Integer requestId);
}
