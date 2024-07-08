package ru.practicum.events;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.model.UpdateEventAdminRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static ru.practicum.stats.Stats.*;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class EventsController {

    private static final String EVENTS = "Events: ";

    private static final String APP = "ewm-main-service";

    @GetMapping("/events")
    public List<EventShortDto> getEvents(
            @RequestParam (required = false) String text,//текст для поиска в содержимом аннотации и подробном описании события
            @RequestParam (required = false) Integer categories, //список идентификаторов категорий в которых будет вестись поиск
            @RequestParam (required = false) Boolean paid, //поиск только платных/бесплатных событий
            @RequestParam (required = false) String rangeStart, //дата и время не раньше которых должно произойти событие
            @RequestParam (required = false) String rangeEnd, //дата и время не позже которых должно произойти событие
            @RequestParam (required = false) Boolean onlyAvailable,//только события у которых не исчерпан лимит запросов на участие
            @RequestParam (required = false) String sort,//Вариант сортировки: по дате события или по количеству просмотров
            @RequestParam (defaultValue = "0") Integer from,
            @RequestParam (defaultValue = "10") Integer size,
            HttpServletRequest request
    ) {
        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable String id, HttpServletRequest request) {

        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }

  /*  @PostMapping("/users/{userId}/events") //Добавление нового события пользователем
    public EventFullDto addEventUser(@RequestBody NewEventDto newEventDto,
                                     @PathVariable String userId,
                                     HttpServletRequest request
    ) {
        log.info("Добавление нового события {} пользователем {}",newEventDto,userId);
        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }*/

    /*@GetMapping("/users/{userId}/events") //Получение событий, добавленных текущим пользователем
    public List<EventShortDto> getEventsAddedCurrentUser(@PathVariable String userId,
                                                         @RequestParam (defaultValue = "0") Integer from,
                                                         @RequestParam (defaultValue = "10") Integer size,
                                                         HttpServletRequest request
    ) {
        log.info("Получение событий, добавленных текущим пользователем {}, в диапазоне от {} до {}",userId, from, size);
        log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }*/

    /*@PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateEventAndStatus(@RequestBody UpdateEventAdminRequest eventAdminRequest,
                                             @PathVariable String eventId,
                                             HttpServletRequest request
    ) {
        log.info("Декодирована {}",decode(eventId));
        log.info("Обновление события {}, {}",eventId,eventAdminRequest);
        log.info("Отправлена статистика {}",getStatsClient().put(hit("ewm-main-service",request)));
    return null;
    }*/

    /*private String decode(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }*/
}
