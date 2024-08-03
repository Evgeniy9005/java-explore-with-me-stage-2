package ru.practicum.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ConflictException;
import ru.practicum.NotFoundException;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.model.Category;
import ru.practicum.constants.State;
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
import ru.practicum.users.request.dto.ParticipationRequestDto;
import ru.practicum.users.dto.UpdateEventUserRequest;
import ru.practicum.users.request.converter.RequestMapper;
import ru.practicum.users.request.dao.RequestRepository;
import ru.practicum.users.request.model.EventIdAndParticipantId;
import ru.practicum.users.request.model.ParticipationRequest;
import ru.practicum.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.practicum.constants.StatusRequest.CONFIRMED;
import static ru.practicum.constants.StatusRequest.REJECTED;
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
        if (categoryId != null) {
            category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new NotFoundException("Не найдена категория # при обновлении события!",categoryId));
        }
        Event event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найдено событие # при обновлении события!",eventId));

        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Запрещено изменение опубликованного события от имени пользователя!");
        }

        User user = userRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Не найден пользователь # при обновлении события!",eventId));

        Event upEvent = patchEventUser(event,eventUserRequest,category,eventId);
        log.info("Обновленное событие после патча {}",upEvent);

        Event newEvent = eventsRepository.save(upEvent);
        log.info("Новое, сохраненное событие {} пользователем {}",newEvent,user);

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
        log.info("Входные параметры body = {}, userId = {} eventId = {}",updateRequest,userId,eventId);

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult(new ArrayList<>(),new ArrayList<>());
        List<Integer> requestIds = updateRequest.getRequestIds();
        StatusRequest status = updateRequest.getStatus();
        List<ParticipationRequest> newPrConfirmedList = new ArrayList<>();
        List<ParticipationRequest> newPrRejectedList = new ArrayList<>();
        List<ParticipationRequest> newPrUnitedList = new ArrayList<>();
        EventIdAndParticipantId ep;
        int pl;
        boolean flag = false;
        String exceptionMessage = null;
        Event event;

        List<ParticipationRequest> prList = requestRepository.findAllById(requestIds);
        log.info("Получены затребованные заявки {} !",requestIds);

        if(prList.isEmpty()) {
            throw new ConflictException(
                    "Пустой список заявок при (подтверждена, отменена) заявок на участие в событии # ",eventId
            );
        } else {
            event = prList.get(0).getEvent();
            pl = event.getParticipantLimit();
        }

        //вернуть количество принятых заявок
        int confirmedRequests = event.getConfirmedRequests();

       // ep = requestRepository.numberEventsAndNumberParticipants(eventId,StatusRequest.CONFIRMED);

        /*if (ep != null) {//значит подтвержденные заявки есть
            confirmedRequests = ep.getCountParticipant();
        }*/

        //надо сделать проверку заполнен ли лимит на участие сравнив лимит с подтвержденными

        //если заполнен лимит на участие в событии, то отменить все последующие заявки

        log.info("Полученное количество подтвержденных заявок {} на событие {}!",confirmedRequests,eventId);

        if (pl != 0 && confirmedRequests == pl) {//значит нужно отменить все непринятые заявки

        }

        if (status.equals(CONFIRMED))//если на подтверждение
         prList.stream()
                 .filter(pr -> !pr.getStatus().equals(CONFIRMED)) //убрать уже принятые заявки

        //надо исключить те заявки которые уже были подтверждены

        for (ParticipationRequest pr : prList) {
            //при отмене заявок должен проверятся статус на отмену и что заявка была принята
            if (status.equals(REJECTED) && pr.getStatus().equals(CONFIRMED)) {
                throw new ConflictException(
                        "Попытка отменить уже принятую заявку # на участие в событии #!",pr.getId(),eventId
                );
            }

            if (pl != 0 && pl <= confirmedRequests) {//говорит о том что лимит заполнен
                flag = true;

                if (exceptionMessage == null) {
                    exceptionMessage = String.format(
                            "Попытка принять заявку на участие в событии, когда лимит %s уже достигнут!",pl);
                }

                //если лимит превышен, то отменить все следующие заявки
                newPrRejectedList.add(pr.toBuilder().status(REJECTED).build());

            }

            confirmedRequests += 1;

            if (status.equals(CONFIRMED)) {
                newPrConfirmedList.add(pr.toBuilder().status(status).build());
            } else if (status.equals(REJECTED)) {
                newPrRejectedList.add(pr.toBuilder().status(status).build());
            } else {
                throw new ConflictException("Ошибка обновления заявок на участие в событиях! " +
                        "Не известное значение статуса #",status);
            }

        }

            log.info("Подготовлены для обновления заявки с измененным статусом на {} в количестве {}!",
                    REJECTED,newPrRejectedList.size());
            log.info("Подготовлены для обновления заявки с измененным статусом на {} в количестве {}!",
                    CONFIRMED,newPrConfirmedList.size());

            newPrUnitedList.addAll(newPrConfirmedList);
            newPrUnitedList.addAll(newPrRejectedList);
            log.info("Объединенный список заявок для сохранения в количестве {}!", newPrUnitedList.size());

            Event newEvent = eventsRepository.save(event.toBuilder().confirmedRequests(confirmedRequests).build());
            log.info("Обновлено значение confirmedRequests = {} у события {} ",newEvent.getConfirmedRequests(),eventId);

            List<ParticipationRequest> newPrList = requestRepository.saveAll(newPrUnitedList);
            log.info("Обновленные заявки на события после обновления в количестве {}!",newPrList.size());

            newPrList.stream()
                    .peek(pr -> {
                     ParticipationRequestDto prDto = requestMapper.toDto(pr);
                                switch (prDto.getStatus()) {
                                    case "CONFIRMED":
                                        result.addConfirmedRequests(prDto);
                                    case "REJECTED":
                                        result.addRejectedRequests(prDto);
                                }

                    });

        if(flag) {
             throw new ConflictException(exceptionMessage);
        }

        return result;
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
        int participantLimit;
        int numberParticipants;
        boolean moderation;
        StatusRequest status;

        Event event = eventsRepository.findById(eventId)
                .orElseThrow(()-> new NotFoundException(
                        "Не найдено событие # при добавлении участия в событии!",eventId)
                );

        participantLimit = event.getParticipantLimit();

        moderation = event.isRequestModeration();

        numberParticipants = requestRepository.numberParticipants(eventId, CONFIRMED);

        log.info("Количество участников = {}, лимит участников = {}, событие {} ",
                numberParticipants,participantLimit,eventId);

        if (event.getInitiator().getId() == userId) {
            throw new ConflictException("Добавление запроса от инициатора # события на участие в нём!",userId);
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Добавление запроса на участие в неопубликованном событии #!",eventId);
        }

        //если модерация отключена, то необходимо смотреть лимит на участников в событии
        if (!moderation && participantLimit != 0 && participantLimit <= numberParticipants) {
            throw new ConflictException("Добавление запроса на участие в событии #," +
                    " у которого заполнен лимит участников = #, всего оставшихся мест # на участие в событии!",
                    eventId,participantLimit,participantLimit - numberParticipants);
        }

        //если для события отключена пре-модерация запросов на участие, то запрос должен автоматически
        //перейти в состояние подтвержденного
        if (moderation) { //true - заявка на событие подтверждается автоматически
            status = CONFIRMED;
        } else { //false - заявка подтверждается инициатором
            status = StatusRequest.PENDING;
        }

        ParticipationRequest pr = ParticipationRequest.builder()
                .requester(userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException(
                                "Не найден пользователь # при добавлении участия в событии!",userId)))
                .created(LocalDateTime.now())
                .event(event)
                .status(status)
                .build();
        ParticipationRequest newPr = requestRepository.save(pr);
        log.info("Добавлен запрос {} от текущего пользователя {} на участие в событии {}",newPr,userId,eventId);
        return requestMapper.toDto(newPr);
    }

    //Отмена своего запроса на участие в событии ("/users/{userId}/requests/{requestId}/cancel")
    @Override
    public ParticipationRequestDto upEventToParticipateCancel (int userId,
                                                               int requestId,
                                                               HttpServletRequest request
    ) {
        ParticipationRequest pr = requestRepository.findById(requestId)
                .orElseThrow(()-> new NotFoundException("Не найден запрос # на событие!",requestId));

        if (pr.getRequester().getId() != userId) {
            throw new NotFoundException("Пользователь # не создавал запрос # на событие!",userId,requestId);
        }

        ParticipationRequest newPr = requestRepository.save(pr.toBuilder().status(StatusRequest.CANCELED).build());

        ParticipationRequestDto newPrDto = requestMapper.toDto(newPr);

        log.info("Получен запрос {} на событие!",newPrDto);

        return newPrDto;
    }

}
