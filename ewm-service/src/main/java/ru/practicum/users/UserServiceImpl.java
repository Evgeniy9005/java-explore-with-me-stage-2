package ru.practicum.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.NotFoundException;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.model.Category;
import ru.practicum.constants.StatusRequest;
import ru.practicum.events.coverter.EventsMapper;
import ru.practicum.events.dao.EventsRepository;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.users.dao.UserRepository;
import ru.practicum.users.model.User;
import ru.practicum.users.request.EventRequestStatusUpdateRequest;
import ru.practicum.users.request.EventRequestStatusUpdateResult;
import ru.practicum.users.request.ParticipationRequestDto;
import ru.practicum.users.dto.UpdateEventUserRequest;
import ru.practicum.users.request.converter.RequestMapper;
import ru.practicum.users.request.dao.RequestRepository;
import ru.practicum.users.request.model.ParticipationRequest;
import ru.practicum.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.util.Patch.patchEventUser;


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
    @Override
    public List<EventShortDto> getEventsAddedCurrentUser(int userId,
                                                         int from,
                                                         int size,
                                                         HttpServletRequest request
    ) {
        log.info("{} Получение событий, добавленных текущим пользователем {}, в диапазоне от {} до {}",USERS,userId, from, size);

        Sort sort = Sort.by(Sort.Direction.ASC,"id");
        List<Event> events = eventsRepository.findByInitiatorId(userId,Util.page(from,size,sort));
        List<EventShortDto> eventShortDtoList = events.stream()
                .map(event -> eventsMapper.toEventShortDto(event))
                .collect(Collectors.toList());
        log.info("Получены события в размере {}",eventShortDtoList.size());
        eventShortDtoList.stream().forEach(e -> log.info(e.toString()));
        //log.info("Отправлена статистика {}",getStatsClient().put(hit(APP,request)));
        return eventShortDtoList;
    }

     //Добавление нового события пользователем
    @Override
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
    @Override
    public EventFullDto getFullInfoAboutEventAddedByCurrentUser(int userId,
                                                                int eventId,
                                                                HttpServletRequest request
    ) {
        Event event = eventsRepository.findByInitiatorIdAndId(userId,eventId)
                .orElseThrow(()-> new NotFoundException("Не найдено события # " +
                        "добавленном текущим пользователем #",userId,eventId));

        EventFullDto eventFullDto = eventsMapper.toEventFullDto(event);

        log.info("Получено событие добавленном текущим пользователем!",eventFullDto);
        log.info(eventFullDto.toString());

        return eventFullDto;
    }

    //Изменение события добавленного текущим пользователем
    @Override
    public EventFullDto upEventAddedByCurrentUser(UpdateEventUserRequest eventUserRequest,
                                                            int userId,
                                                            int eventId,
                                                            HttpServletRequest request
    ) {
        log.info("Входные параметры userid = {}, eventId = {}, body = {}",userId,eventId,eventUserRequest);
        Integer categoryId = eventUserRequest.getCategory();
        Category category = null;
        if(categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException("Не найдена категория # при обновлении события!",categoryId));
        }
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найдено событие # при обновлении события!",eventId));
        User user = userRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь # при обновлении события!",eventId));

        Event upEvent = patchEventUser(event,eventUserRequest,category,eventId);
        log.info("Обновленное событие после патча {}",upEvent);
        Event newEvent = eventsRepository.save(upEvent);
        log.info("Новое, сохраненное событие {}",newEvent);

        return eventsMapper.toEventFullDto(newEvent);
    }

    //Получение информации о запросах на участие в событии текущего пользователя
    @Override
    public List<ParticipationRequestDto> getInformationRequestsToParticipateCurrentUserEvent(int userId,
                                                                                             int eventId,
                                                                                             HttpServletRequest request
    ) {
        List<ParticipationRequest> prList = requestRepository.findByEventInitiatorIdAndEventId(userId,eventId);
        List<ParticipationRequestDto> prDtoList = prList.stream()
                .map(pr -> requestMapper.toDto(pr))
                .collect(Collectors.toList());

        log.info("Получена информация о запросах на участие в событии текущего пользователя! Всего {}",prDtoList.size());
        prDtoList.stream().forEach(prDto -> log.info(prDto.toString()));

        return prDtoList;
    }

    //Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
    @Override
    public EventRequestStatusUpdateResult upStatusApplicationsParticipationEventCurrentUser(
            EventRequestStatusUpdateRequest updateRequest,
            int userId,
            int eventId,
            HttpServletRequest request
    ) {
        log.info("Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя");
        log.info("Входные параметры dody = {}, userId = {} eventId = {}",updateRequest,userId,eventId);
        List<Integer> requestIds = updateRequest.getRequestIds();

        StatusRequest s = updateRequest.getStatus();
        List<ParticipationRequestDto> prl = null;
        if(requestIds != null && !requestIds.isEmpty()) {
            prl = requestRepository.findAllById(requestIds).stream()
                    .map(pr -> requestRepository.save(pr.toBuilder().status(s).build()))
                    .map(prDto -> requestMapper.toDto(prDto))
                    .collect(Collectors.toList());
        }

        switch (s) {
            case CONFIRMED:
                return new EventRequestStatusUpdateResult(prl,new ArrayList<>());
            case REJECTED:
                return new EventRequestStatusUpdateResult(new ArrayList<>(),prl);
        }

        return null;
    }

    //Получение информации о заявках текущего пользователя на участие в чужих событиях
    @Override
    public List<ParticipationRequestDto> getInfoCurrentUserRequestsParticipateOtherPeopleEvents(
            int userId,
            HttpServletRequest request
    ) {
        List<ParticipationRequest> prList = requestRepository.findByRequesterId(userId);
        List<ParticipationRequestDto> prDtoList = prList.stream()
                .map(pr -> requestMapper.toDto(pr))
                .collect(Collectors.toList());
        log.info("Получена информация о заявках текущего пользователя на участие в чужих событиях! " +
                "Всего {}",prDtoList.size());
        prDtoList.stream().forEach(prDto -> log.info(prDto.toString()));

        return prDtoList;
    }

    //Добавление запроса от текущего пользователя на участие в событии
    @Override
    public ParticipationRequestDto addRequestCurrentUserParticipateEvent(int userId,
                                                                         int eventId,
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
    @Override
    public ParticipationRequestDto upEventToParticipateCancel (int userId,
                                                               int requestId,
                                                               HttpServletRequest request
    ) {
        ParticipationRequest pr = requestRepository.findById(requestId)
                .orElseThrow(()-> new NotFoundException("Не найден запрос # на событие!",requestId));

        if (pr.getRequester().getId() != userId) {
            throw new NotFoundException("Пользователь # не создавал запрос # на событие",userId,requestId);
        }

        log.info("Получен запрос {} на событие!",pr);

        return requestMapper.toDto(pr.toBuilder().status(StatusRequest.CANCELED).build());
    }

}
