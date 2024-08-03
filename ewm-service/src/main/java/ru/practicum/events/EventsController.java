package ru.practicum.events;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.annotations.IdsValidate;
import ru.practicum.constants.SortEvents;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
public class EventsController {

    private static final String EVENTS = "Events: ";

    private static final String APP = "ewm-main-service";

    private final EventsService eventsService;

    @GetMapping("/events")
    public List<EventShortDto> getEvents(
            @RequestParam (required = false) String text,//текст для поиска в содержимом аннотации и подробном описании события
            @RequestParam (required = false) @IdsValidate List<Integer> categories, //список идентификаторов категорий в которых будет вестись поиск
            @RequestParam (required = false) Boolean paid, //поиск только платных/бесплатных событий
            @RequestParam (required = false) String rangeStart, //дата и время не раньше которых должно произойти событие
            @RequestParam (required = false) String rangeEnd, //дата и время не позже которых должно произойти событие
            @RequestParam (defaultValue = "false") boolean onlyAvailable,//только события у которых не исчерпан лимит запросов на участие
            @RequestParam (defaultValue = "EVENT_DATE") SortEvents sort,//Вариант сортировки: по дате события или по количеству просмотров
            @RequestParam (defaultValue = "0") int from,
            @RequestParam (defaultValue = "10") int size,
            HttpServletRequest request
    ) {

        return eventsService.getEvents(text,categories,paid,rangeStart,rangeEnd,onlyAvailable, sort,from,size,request);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getEvent(@PathVariable @Positive int id, HttpServletRequest request) {

        return eventsService.getEvent(id,request);
    }

}
