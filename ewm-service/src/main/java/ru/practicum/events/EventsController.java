package ru.practicum.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatsClient;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.model.UpdateEventAdminRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.stats.Stats.*;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class EventsController {

    @GetMapping("/events")
    public List<EventShortDto> getEvents(
            @RequestParam (required = false) String text,
            @RequestParam (required = false) Integer categories,
            @RequestParam (required = false) Boolean paid,
            @RequestParam (required = false) String rangeStart,
            @RequestParam (required = false) String rangeEnd,
            @RequestParam (required = false) Boolean onlyAvailable,
            @RequestParam (required = false) String sort,
            @RequestParam (defaultValue = "0") Integer from,
            @RequestParam (defaultValue = "10") Integer size,
            HttpServletRequest request
    ) {
        log.info("Отправлена статистика {}",getStatsClient().put(hit("ewm-main-service",request)));
        return null;
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable String id, HttpServletRequest request) {

        log.info("Отправлена статистика {}",getStatsClient().put(hit("ewm-main-service",request)));
        return null;
    }

    @PostMapping("/users/{userId}/events") //Добавление нового события пользователем
    public EventFullDto addEventUser(@RequestBody NewEventDto newEventDto,
                                     @PathVariable String userId,
                                     HttpServletRequest request
    ) {
        log.info("Добавление нового события {} пользователем {}",newEventDto,userId);
        log.info("Отправлена статистика {}",getStatsClient().put(hit("ewm-main-service",request)));
        return null;
    }

    @GetMapping("/users/{userId}/events") //Получение событий, добавленных текущим пользователем
    public EventShortDto GetEventsAddedCurrentUser(@PathVariable String userId,
                                                   @RequestParam (defaultValue = "0") Integer from,
                                                   @RequestParam (defaultValue = "10") Integer size,
                                                   HttpServletRequest request
    ) {
        log.info("Получение событий, добавленных текущим пользователем {}, в диапазоне от {} до {}",userId, from, size);
        log.info("Отправлена статистика {}",getStatsClient().put(hit("ewm-main-service",request)));
        return null;
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateEventAndStatus(@RequestBody UpdateEventAdminRequest eventAdminRequest,
                                             @PathVariable String eventId,
                                             HttpServletRequest request
    ) {
        log.info("Декодирована {}",decode(eventId));
        log.info("Обновление события {}, {}",eventId,eventAdminRequest);
        log.info("Отправлена статистика {}",getStatsClient().put(hit("ewm-main-service",request)));
    return null;
    }

    private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
