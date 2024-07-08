package ru.practicum.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.dto.NewUserRequest;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.users.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.users.dto.request.ParticipationRequestDto;
import ru.practicum.users.model.UpdateEventUserRequest;

import javax.servlet.http.HttpServletRequest;

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

   /* @PostMapping("/admin/users")
    public UserDto addNewUser(@RequestBody NewUserRequest newUserRequest, HttpServletRequest request) {
        log.info("{} запрос на добавления пользователя {} ", USERS, newUserRequest);
        log.info("{} отправлена статистика {}", USERS,getStatsClient().put(hit("ewm-main-service",request)));
        return null;
    }*/

    @GetMapping("/users/{userId}/events") //Получение событий, добавленных текущим пользователем
    public List<EventShortDto> getEventsAddedCurrentUser(@PathVariable String userId,
                                                         @RequestParam (defaultValue = "0") Integer from,
                                                         @RequestParam (defaultValue = "10") Integer size,
                                                         HttpServletRequest request
    ) {
        log.info("{} Получение событий, добавленных текущим пользователем {}, в диапазоне от {} до {}",USERS,userId, from, size);
        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }

    @PostMapping("/users/{userId}/events") //Добавление нового события пользователем
    public EventFullDto addEventUser(@RequestBody NewEventDto newEventDto,
                                     @PathVariable String userId,
                                     HttpServletRequest request
    ) {
        log.info("{} Добавление нового события {} пользователем {}",USERS,newEventDto,userId);
        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }

    @GetMapping("/users/{userId}/events/{eventId}") //Получение полной информации о событии добавленном текущим пользователем
    public EventFullDto getFullInfoAboutEventAddedByCurrentUser(@PathVariable Integer userId,
                                                                @PathVariable Integer eventId,
                                                                HttpServletRequest request
    ) {
        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }

    @PatchMapping("/users/{userId}/events/{eventId}") //Изменение события добавленного текущим пользователем
    public UpdateEventUserRequest upEventAddedByCurrentUser(@PathVariable Integer userId,
                                                            @PathVariable Integer eventId,
                                                            HttpServletRequest request
    ) {
        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }

    @GetMapping("/users/{userId}/events/{eventId}/requests") //Получение информации о запросах на участие в событии текущего пользователя
    public List<ParticipationRequestDto> getInformationRequestsToParticipateCurrentUserEvent(@PathVariable Integer userId,
                                                                                       @PathVariable Integer eventId,
                                                                                       HttpServletRequest request
    ) {
        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }

    @PatchMapping("/users/{userId}/events/{eventId}/requests") //Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
    public EventRequestStatusUpdateResult upStatusApplicationsParticipationEventCurrentUser(
            @RequestBody EventRequestStatusUpdateRequest updateRequest,
            @PathVariable Integer userId,
            @PathVariable Integer eventId,
            HttpServletRequest request
    ) {
        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }

    @GetMapping("/users/{userId}/requests") //Получение информации о заявках текущего пользователя на участие в чужих событиях
    public List<ParticipationRequestDto> getInfoCurrentUserRequestsParticipateOtherPeopleEvents(
            @PathVariable Integer userId,
            HttpServletRequest request
    ) {
        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }
}
