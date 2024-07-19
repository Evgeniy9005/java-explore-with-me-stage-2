package ru.practicum.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.users.request.EventRequestStatusUpdateRequest;
import ru.practicum.users.request.EventRequestStatusUpdateResult;
import ru.practicum.users.request.ParticipationRequestDto;
import ru.practicum.users.dto.UpdateEventUserRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;

import java.util.List;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class UsersController {
    private static final String USERS = "Users: ";

    private static final String APP = "ewm-main-service";

    private final UserService userService;


    @GetMapping("/users/{userId}/events") //Получение событий, добавленных текущим пользователем
    public List<EventShortDto> getEventsAddedCurrentUser(@PathVariable int userId,
                                                         @RequestParam (defaultValue = "0") int from,
                                                         @RequestParam (defaultValue = "10") int size,
                                                         HttpServletRequest request
    ) {

        return userService.getEventsAddedCurrentUser(userId,from,size,request);
    }

    @PostMapping("/users/{userId}/events") //Добавление нового события пользователем
    @ResponseStatus(code = HttpStatus.CREATED)
    public EventFullDto addEventUser(@RequestBody NewEventDto newEventDto,
                                     @PathVariable @Positive int userId,
                                     HttpServletRequest request
    ) {

        return userService.addEventUser(newEventDto,userId,request);
    }

    @GetMapping("/users/{userId}/events/{eventId}") //Получение полной информации о событии добавленном текущим пользователем
    public EventFullDto getFullInfoAboutEventAddedByCurrentUser(@PathVariable @Positive int userId,
                                                                @PathVariable @Positive int eventId,
                                                                HttpServletRequest request
    ) {

       // log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return userService.getFullInfoAboutEventAddedByCurrentUser(userId,eventId,request);
    }

    @PatchMapping("/users/{userId}/events/{eventId}") //Изменение события добавленного текущим пользователем
    public EventFullDto upEventAddedByCurrentUser(@RequestBody UpdateEventUserRequest eventUserRequest,
                                                  @PathVariable @Positive int userId,
                                                  @PathVariable @Positive int eventId,
                                                  HttpServletRequest request
    ) {

        return userService.upEventAddedByCurrentUser(eventUserRequest,userId,eventId,request);
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests") //Получение информации о запросах на участие в событии текущего пользователя
    public List<ParticipationRequestDto> getInformationRequestsToParticipateCurrentUserEvent(@PathVariable int userId,
                                                                                       @PathVariable int eventId,
                                                                                       HttpServletRequest request
    ) {
        //log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return userService.getInformationRequestsToParticipateCurrentUserEvent(userId,eventId,request);
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests") //Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
    public EventRequestStatusUpdateResult upStatusApplicationsParticipationEventCurrentUser(
            @RequestBody EventRequestStatusUpdateRequest updateRequest,
            @PathVariable @Positive int userId,
            @PathVariable @Positive int eventId,
            HttpServletRequest request
    ) {
        return userService.upStatusApplicationsParticipationEventCurrentUser(updateRequest,userId,eventId,request);
    }

    @GetMapping("/users/{userId}/requests") //Получение информации о заявках текущего пользователя на участие в чужих событиях
    public List<ParticipationRequestDto> getInfoCurrentUserRequestsParticipateOtherPeopleEvents(
            @PathVariable @Positive int userId,
            HttpServletRequest request
    ) {
      //  log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return userService.getInfoCurrentUserRequestsParticipateOtherPeopleEvents(userId,request);
    }

    @PostMapping("/users/{userId}/requests") //Добавление запроса от текущего пользователя на участие в событии
    @ResponseStatus(code = HttpStatus.CREATED)
    public ParticipationRequestDto addRequestCurrentUserParticipateEvent(
            @PathVariable @Positive int userId,
            @RequestParam @Positive int eventId,
            HttpServletRequest request
    ) {

        return userService.addRequestCurrentUserParticipateEvent(userId,eventId,request);
    }

    @PatchMapping("/users/{userId}/requests/{requestId}/cancel") //Отмена своего запроса на участие в событии
    public ParticipationRequestDto upEventToParticipateCancel (
            @PathVariable @Positive int userId,
            @PathVariable @Positive int requestId,
            HttpServletRequest request
    ) {

        return userService.upEventToParticipateCancel(userId,requestId,request);
    }
}
