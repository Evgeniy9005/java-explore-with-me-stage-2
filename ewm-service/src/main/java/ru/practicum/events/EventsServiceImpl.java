package ru.practicum.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.NotFoundException;
import ru.practicum.constants.SortEvents;
import ru.practicum.events.coverter.EventsMapper;
import ru.practicum.events.dao.CustomizedEventRepository;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.Event;
import ru.practicum.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        log.info("Входные параметры text = {}, categories = {}, paid = {}, " +
                        "rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}, from = {}, size = {},",
                text,categories,paid,rangeStart,rangeEnd,onlyAvailable, sort,from,size);

        List<Event> eventList;

        String qSort = "order by e.eventDate ";

        Map<String,Object> param = new HashMap<>();

        String qParticipantLimit = "";

        String qPaid = ""; //учет платные или бесплатные

        String qCategories = "and e.category.id in(:categories) ";

        String qText = "and UPPER(e.annotation) like UPPER(:text) ";

        if(onlyAvailable) { //только события у которых не исчерпан лимит запросов на участие
            qParticipantLimit = "and (count(pr.id) < e.participantLimit or e.participantLimit = 0)";
        }

        if(paid == null) { //поиск платных и бесплатных событий
           qPaid = "and e.paid = :paid ";
        }

        //если в запросе не указан диапазон дат [rangeStart-rangeEnd],
        //то нужно выгружать события, которые произойдут позже текущей даты и времени

        switch (sort) {
            case EVENT_VIEWS:
                qSort = "order by e.views ";
                break;
            default:

        }

        String query = "select e from Event e " + //исчерпан лимит запросов на участие
                "join ParticipationRequest pr on e.id = pr.event.id " +
                "group by pr.id " +
                "having e.eventDate >= :rangeStart " +
                "and e.eventDate <= :rangeEnd " +
                qText +
                qCategories +
                qPaid + //учет платные или бесплатные
                qParticipantLimit +
                qSort;

        eventList = eventsRepository.searchE();

        log.info("ВВВВВВВВВ {}", eventList);


        List<EventShortDto> eventShortDtoList = eventList.stream()
                .map(event -> eventsMapper.toEventShortDto(event))
                .collect(Collectors.toList());
        log.info("Получено событий в размере {}",eventShortDtoList.size());
        eventShortDtoList.stream().forEach(event -> log.info(event.toString()));
        return eventShortDtoList;
    }

    @Override
    public EventFullDto getEvent(int id, HttpServletRequest request) {
        Event event = eventsRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Не найдено событие под id = {}",id));

        EventFullDto eventFullDto = eventsMapper.toEventFullDto(event);

        log.info("Получено событие {}",eventFullDto);

       // log.info("{} Отправлена статистика {}",EVENTS,getStatsClient().put(hit(APP,request)));
        return eventFullDto;
    }
}
