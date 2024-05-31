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
import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.practicum.stats.Stats.*;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
public class EventsController {

    @GetMapping
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

    @GetMapping("/{id}")
    public EventFullDto getEvent(@PathVariable Integer id, HttpServletRequest request) {

        log.info("Отправлена статистика {}",getStatsClient().put(hit("ewm-main-service",request)));
        return null;
    }


}
