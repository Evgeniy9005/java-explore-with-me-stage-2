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
        log.info("{} Отправлена статистика {}",EVENTS,getStatsClient().put(hit(APP,request)));
        return null;
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable String id, HttpServletRequest request) {

        log.info("{} Отправлена статистика {}",EVENTS,getStatsClient().put(hit(APP,request)));
        return null;
    }

}
