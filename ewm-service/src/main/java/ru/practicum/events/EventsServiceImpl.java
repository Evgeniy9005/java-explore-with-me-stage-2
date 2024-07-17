package ru.practicum.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.events.coverter.EventsMapper;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static ru.practicum.stats.Stats.getStatsClient;
import static ru.practicum.stats.Stats.hit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private static final String EVENTS = "Events: ";

    private static final String APP = "ewm-main-service";

    private final EventsRepository eventsRepository;

    private final EventsMapper eventsMapper;

    public List<EventShortDto> getEvents(
            String text,//текст для поиска в содержимом аннотации и подробном описании события
            Integer categories, //список идентификаторов категорий в которых будет вестись поиск
            Boolean paid, //поиск только платных/бесплатных событий
            String rangeStart, //дата и время не раньше которых должно произойти событие
            String rangeEnd, //дата и время не позже которых должно произойти событие
            Boolean onlyAvailable,//только события у которых не исчерпан лимит запросов на участие
            String sort,//Вариант сортировки: по дате события или по количеству просмотров
            Integer from,
            Integer size,
            HttpServletRequest request
    ) {
        //log.info("{} Отправлена статистика {}",EVENTS,getStatsClient().put(hit(APP,request)));


        return null;
    }


    public EventFullDto getEvent(String id, HttpServletRequest request) {

       // log.info("{} Отправлена статистика {}",EVENTS,getStatsClient().put(hit(APP,request)));
        return null;
    }
}
