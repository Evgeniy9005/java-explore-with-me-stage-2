package ru.practicum.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.NotFoundException;
import ru.practicum.constants.SortEvents;
import ru.practicum.constants.State;
import ru.practicum.events.coverter.EventsMapper;
import ru.practicum.events.dao.CustomizedEventRepository;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.Event;
import ru.practicum.util.Util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private Map<Integer,List<String>> address = new HashMap<>();

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
        log.info("{} Отправлена статистика {}",EVENTS,getStatsClient().put(hit(APP,request)));

        log.info("Входные параметры text = {}, categories = {}, paid = {}, " +
                        "rangeStart = {}, rangeEnd = {}, onlyAvailable = {}, sort = {}, from = {}, size = {}!",
                text,categories,paid,rangeStart,rangeEnd,onlyAvailable, sort,from,size);

        List<Event> eventList;

        String qSort = "order by e.eventDate asc ";

        Map<String,Object> param = new HashMap<>();

        String qParticipantLimit = "";

        String qPaid = ""; //учет платные или бесплатные

        String qCategories = "";

        String qText = "";

        param.put("rangeStart",Util.getDateStart(rangeStart));
        param.put("rangeEnd",Util.getDateEnd(rangeEnd));

        if(onlyAvailable) { //только события у которых не исчерпан лимит запросов на участие
          //  qParticipantLimit = "and (count(pr.id) < e.participantLimit or e.participantLimit = 0)";
            qParticipantLimit = "and e.confirmedRequests < e.participantLimit or e.participantLimit = 0) ";
        }

        if(paid != null) { //поиск платных и бесплатных событий
           qPaid = "and e.paid = :paid ";
           param.put("paid",paid);
        }

        if(categories != null) {
            qCategories = "and e.category.id in(:categories) ";
            param.put("categories",categories);
        }

        if(text != null) {
            qText = "and UPPER(e.annotation) like UPPER(:text) ";
            param.put("text",text);
        }

        log.info("Параметры запроса для привязки {}!",param);

        //если в запросе не указан диапазон дат [rangeStart-rangeEnd],
        //то нужно выгружать события, которые произойдут позже текущей даты и времени

        switch (sort) {
            case EVENT_VIEWS:
                qSort = "order by e.views asc ";
                break;
            default:
                qSort = "order by e.eventDate asc ";
        }

        String query = "select e from Event e " + //исчерпан лимит запросов на участие
                "where e.eventDate >= :rangeStart " +
                "and e.eventDate <= :rangeEnd " +
                qText +
                qCategories +
                qPaid + //учет платные или бесплатные
                qParticipantLimit +
                qSort;

        log.info("Запрос на выборку! {}",query);

        eventList = eventsRepository.searchE(query,param,from,size);

        List<EventShortDto> eventShortDtoList = eventList.stream()
                .map(event -> eventsMapper.toEventShortDto(event))
                .collect(Collectors.toList());

        log.info("Получено событий в размере {}",eventShortDtoList.size());

        eventShortDtoList.stream().forEach(event -> log.info(event.toString()));

        return eventShortDtoList;
    }

    @Override //для публичного эндпоинта можно вернуть только опубликованные события
    public EventFullDto getEvent(int id, HttpServletRequest request) {
        log.info("{} Отправлена статистика {}",EVENTS,getStatsClient().put(hit(APP,request)));
        String ip = request.getRemoteAddr();

        Event event = eventsRepository.findByIdAndState(id, State.PUBLISHED)
                .orElseThrow(()-> new NotFoundException("Не найдено событие под id = #",id));

        int views = event.getViews();

        if (searchIP(id,ip)) {
            views += 1;
        }

        Event newEvent = eventsRepository.save(event.toBuilder().views(views).build());

        EventFullDto eventFullDto = eventsMapper.toEventFullDto(newEvent);

        log.info("Получено событие {}",eventFullDto);

        return eventFullDto;
    }

    private boolean searchIP(int eventId, String ip) {
        List<String> ipList = new ArrayList<>();

        if(address.containsKey(eventId)) {//если событие просматривалось
            if(address.get(eventId).contains(ip)) {//с этого ip был запрос на просмотр
                return false;
            } else { //если не было, то запомнить
                address.get(eventId).add(ip);
                return true;
            }
        } else {//если событие не просматривалось
            ipList.add(ip);
            address.put(eventId,ipList);
            return true;
        }
    }
}
