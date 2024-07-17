package ru.practicum.events;


import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventsService {
    List<EventShortDto> getEvents(
             String text,//текст для поиска в содержимом аннотации и подробном описании события
             List<Integer> categories, //список идентификаторов категорий в которых будет вестись поиск
             Boolean paid, //поиск только платных/бесплатных событий
             String rangeStart, //дата и время не раньше которых должно произойти событие
             String rangeEnd, //дата и время не позже которых должно произойти событие
             Boolean onlyAvailable,//только события у которых не исчерпан лимит запросов на участие
             String sort,//Вариант сортировки: по дате события или по количеству просмотров
             Integer from,
             Integer size,
             HttpServletRequest request
    );

    EventFullDto getEvent(String id, HttpServletRequest request);
}
