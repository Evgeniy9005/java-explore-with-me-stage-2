package ru.practicum.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.NotFoundException;
import ru.practicum.constants.SortEvents;
import ru.practicum.events.coverter.EventsMapper;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.Event;
import ru.practicum.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private static final String EVENTS = "Events: ";

    private static final String APP = "ewm-main-service";

    private final EventsRepository eventsRepository;

    private final EventsMapper eventsMapper;

    @Override
    public List<EventShortDto> getEvents(
            String text,//текст для поиска в содержимом аннотации и подробном описании события
            List<Integer> categories, //список идентификаторов категорий в которых будет вестись поиск
            Boolean paid, //поиск только платных/бесплатных событий
            String rangeStart, //дата и время не раньше которых должно произойти событие
            String rangeEnd, //дата и время не позже которых должно произойти событие
            boolean onlyAvailable,//только события у которых не исчерпан лимит запросов на участие
            SortEvents sort,//Вариант сортировки: по дате события или по количеству просмотров
            int from,
            int size,
            HttpServletRequest request
    ) {
        //log.info("{} Отправлена статистика {}",EVENTS,getStatsClient().put(hit(APP,request)));
        LocalDateTime start;
        LocalDateTime end;
        List<Event> eventList;
        Sort sortEvents = Sort.by(Sort.Direction.ASC,"id");
        log.info("Входные параметры text = {}, categories = {}, paid = {}, " +
                "rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}, from = {}, size = {},",
                text,categories,paid,rangeStart,rangeEnd,onlyAvailable, sort,from,size);

        //если в запросе не указан диапазон дат [rangeStart-rangeEnd],
        //то нужно выгружать события, которые произойдут позже текущей даты и времени
        if (rangeStart == null) {
            start = LocalDateTime.now();
        } else {
            start = Util.getDate(rangeStart);
        }

        if (rangeEnd == null) {
            end = start.plusYears(10);
        } else {
            end = Util.getDate(rangeEnd);
        }

        switch (sort) {
            case EVENT_DATE:
                sortEvents = Sort.by(Sort.Direction.ASC,"eventDate");
        }

        if(onlyAvailable) {
            eventList = eventsRepository.searchEventsLimitNumberRequests(text,
                    categories,
                    paid,
                    start,
                    end,
                    Util.page(from,size,sortEvents));
        } else {
            eventList = eventsRepository.searchEvents(text,
                    categories,
                    paid,
                    start,
                    end,
                    Util.page(from,size,sortEvents));
        }

        List<EventShortDto> eventShortDtoList = eventList.stream()
                .map(event -> eventsMapper.toEventShortDto(event))
                .collect(Collectors.toList());
        log.info("Получено событий в размере {}",eventShortDtoList.size());
        eventShortDtoList.stream().forEach(event -> log.info(event.toString()));
        return eventShortDtoList;
    }

    @Override
    public EventFullDto getEvent(int id, HttpServletRequest request) {
        eventsRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Не найдено событие под id = {}",id));

       // log.info("{} Отправлена статистика {}",EVENTS,getStatsClient().put(hit(APP,request)));
        return null;
    }
}
