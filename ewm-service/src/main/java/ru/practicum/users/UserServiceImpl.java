package ru.practicum.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.users.dao.UserRepository;
import ru.practicum.users.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.users.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.users.dto.request.ParticipationRequestDto;
import ru.practicum.users.model.UpdateEventUserRequest;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    //Получение событий, добавленных текущим пользователем
    public List<EventShortDto> getEventsAddedCurrentUser(String userId, Integer from, Integer size) {
        return null;
    }

     //Добавление нового события пользователем
    public EventFullDto addEventUser(NewEventDto newEventDto, String userId) {
        return null;
    }

    //Получение полной информации о событии добавленном текущим пользователем
    public EventFullDto getFullInfoAboutEventAddedByCurrentUser(Integer userId, Integer eventId) {
        return null;
    }

    //Изменение события добавленного текущим пользователем
    public UpdateEventUserRequest upEventAddedByCurrentUser(Integer userId, Integer eventId) {
        return null;
    }

    //Получение информации о запросах на участие в событии текущего пользователя
    public List<ParticipationRequestDto> getInformationRequestsToParticipateCurrentUserEvent(Integer userId,
                                                                                             Integer eventId
    ) {
        return null;
    }

    //Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
    public EventRequestStatusUpdateResult upStatusApplicationsParticipationEventCurrentUser(
            EventRequestStatusUpdateRequest updateRequest,
            Integer userId,
            Integer eventId
    ) {
        return null;
    }

    //Получение информации о заявках текущего пользователя на участие в чужих событиях
    public List<ParticipationRequestDto> getInfoCurrentUserRequestsParticipateOtherPeopleEvents(Integer userId) {
        return null;
    }

    //Добавление запроса от текущего пользователя на участие в событии
    public ParticipationRequestDto addRequestCurrentUserParticipateEvent(Integer userId, Integer eventId) {
        return null;
    }

    //Отмена своего запроса на участие в событии
    public ParticipationRequestDto upEventToParticipateCancel (Integer userId, Integer requestId) {
        return null;
    }

}
