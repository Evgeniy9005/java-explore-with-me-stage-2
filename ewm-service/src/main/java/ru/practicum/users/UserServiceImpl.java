package ru.practicum.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.NotFoundException;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.constants.StatusRequest;
import ru.practicum.events.coverter.EventsMapper;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.users.dao.UserRepository;
import ru.practicum.users.request.EventRequestStatusUpdateRequest;
import ru.practicum.users.request.EventRequestStatusUpdateResult;
import ru.practicum.users.request.ParticipationRequestDto;
import ru.practicum.users.model.UpdateEventUserRequest;
import ru.practicum.users.request.converter.RequestMapper;
import ru.practicum.users.request.dao.RequestRepository;
import ru.practicum.users.request.model.ParticipationRequest;
import ru.practicum.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USERS = "Users: ";

    private static final String APP = "ewm-main-service";

    private final UserRepository userRepository;

    private final EventsRepository eventsRepository;

    private final EventsMapper eventsMapper;

    private final CategoryRepository categoryRepository;

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    //Получение событий, добавленных текущим пользователем
    public List<EventShortDto> getEventsAddedCurrentUser(String userId,
                                                         Integer from,
                                                         Integer size,
                                                         HttpServletRequest request
    ) {
        log.info("{} Получение событий, добавленных текущим пользователем {}, в диапазоне от {} до {}",USERS,userId, from, size);
        //log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return null;
    }

     //Добавление нового события пользователем
    public EventFullDto addEventUser(NewEventDto newEventDto, int userId, HttpServletRequest request) {
        log.info("{} Добавление нового события {} пользователем {}",USERS,newEventDto,userId);
       // log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        Location location = newEventDto.getLocation();

        Event event = Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(categoryRepository.findById(newEventDto.getCategory())
                        .orElseThrow(() -> new NotFoundException("Не найдена категория при добавлении события!")))
                .description(newEventDto.getDescription())
                .eventDate(Util.getDate(newEventDto.getEventDate()))
                .lat(location.getLat())
                .lon(location.getLon())
                .paid(newEventDto.isPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .title(newEventDto.getTitle())
                .initiator(userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException("Не найден пользователь при добавлении события")))
                .build();

        Event newEvent = eventsRepository.save(event);

        log.info( "Создано событие {}",newEvent);

        EventFullDto eventFullDto = eventsMapper.toEventFullDto(event);
        log.info("Конвертированное событие {}",eventFullDto);
        return eventFullDto;
    }

    //Получение полной информации о событии добавленном текущим пользователем
    public EventFullDto getFullInfoAboutEventAddedByCurrentUser(Integer userId,
                                                                Integer eventId,
                                                                HttpServletRequest request
    ) {
        return null;
    }

    //Изменение события добавленного текущим пользователем
    public UpdateEventUserRequest upEventAddedByCurrentUser(Integer userId,
                                                            Integer eventId,
                                                            HttpServletRequest request
    ) {
        return null;
    }

    //Получение информации о запросах на участие в событии текущего пользователя
    public List<ParticipationRequestDto> getInformationRequestsToParticipateCurrentUserEvent(Integer userId,
                                                                                             Integer eventId,
                                                                                             HttpServletRequest request
    ) {
        return null;
    }

    //Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
    public EventRequestStatusUpdateResult upStatusApplicationsParticipationEventCurrentUser(
            EventRequestStatusUpdateRequest updateRequest,
            Integer userId,
            Integer eventId,
            HttpServletRequest request
    ) {
        return null;
    }

    //Получение информации о заявках текущего пользователя на участие в чужих событиях
    public List<ParticipationRequestDto> getInfoCurrentUserRequestsParticipateOtherPeopleEvents(
            Integer userId,
            HttpServletRequest request
    ) {
        return null;
    }

    //Добавление запроса от текущего пользователя на участие в событии
    public ParticipationRequestDto addRequestCurrentUserParticipateEvent(Integer userId,
                                                                         Integer eventId,
                                                                         HttpServletRequest request
    ) {
        ParticipationRequest pr = ParticipationRequest.builder()
                .requester(userRepository.findById(userId)
                        .orElseThrow(()->new NotFoundException("Не найден пользователь # при добавлении участия в событии!",userId)))
                .created(LocalDateTime.now())
                .event(eventsRepository.findById(eventId)
                        .orElseThrow(()-> new NotFoundException("Не найдено событие # при добавлении участия в событии!",eventId)))
                .status(StatusRequest.PENDING)
                .build();
        ParticipationRequest newPr = requestRepository.save(pr);
        log.info("Добавлен запрос {} от текущего пользователя {} на участие в событии {}",newPr,userId,eventId);
        return requestMapper.toDto(newPr);
    }

    //Отмена своего запроса на участие в событии
    public ParticipationRequestDto upEventToParticipateCancel (Integer userId,
                                                               Integer requestId,
                                                               HttpServletRequest request
    ) {
        return null;
    }

}
