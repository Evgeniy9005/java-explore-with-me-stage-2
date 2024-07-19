package ru.practicum.users;

import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.users.request.EventRequestStatusUpdateRequest;
import ru.practicum.users.request.EventRequestStatusUpdateResult;
import ru.practicum.users.request.ParticipationRequestDto;
import ru.practicum.users.dto.UpdateEventUserRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    /**Получение событий, добавленных текущим пользователем*/
    List<EventShortDto> getEventsAddedCurrentUser(int userId,
                                                  int from,
                                                  int size,
                                                  HttpServletRequest request);
    /**Добавление нового события пользователем*/
    EventFullDto addEventUser(NewEventDto newEventDto, int userId, HttpServletRequest request);
    /**Получение полной информации о событии добавленном текущим пользователем*/
    EventFullDto getFullInfoAboutEventAddedByCurrentUser(int userId, int eventId, HttpServletRequest request);
    /**Изменение события добавленного текущим пользователем*/
    EventFullDto upEventAddedByCurrentUser(UpdateEventUserRequest eventUserRequest,
                                           int userId,
                                           int eventId,
                                           HttpServletRequest request);
   /**Получение информации о запросах на участие в событии текущего пользователя*/
    List<ParticipationRequestDto> getInformationRequestsToParticipateCurrentUserEvent(Integer userId,
                                                                                      Integer eventId,
                                                                                      HttpServletRequest request
    );
    /**Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя*/
    EventRequestStatusUpdateResult upStatusApplicationsParticipationEventCurrentUser(
            EventRequestStatusUpdateRequest updateRequest,
            int userId,
            int eventId,
            HttpServletRequest request
    );
    /**Получение информации о заявках текущего пользователя на участие в чужих событиях*/
    List<ParticipationRequestDto> getInfoCurrentUserRequestsParticipateOtherPeopleEvents(int userId,
                                                                                         HttpServletRequest request);
    /**Добавление запроса от текущего пользователя на участие в событии*/
    ParticipationRequestDto addRequestCurrentUserParticipateEvent(int userId,
                                                                  int eventId,
                                                                  HttpServletRequest request
    );
    /**Отмена своего запроса на участие в событии*/
    ParticipationRequestDto upEventToParticipateCancel (int userId,
                                                        int requestId,
                                                        HttpServletRequest request
    );
}
