package ru.practicum.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.practicum.ConflictException;
import ru.practicum.admin.dto.UpdateEventAdminRequest;
import ru.practicum.category.model.Category;
import ru.practicum.compilations.dto.UpdateCompilationRequest;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.constants.State;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;
import ru.practicum.users.dto.UpdateEventUserRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Patch {
    public static Event patchEventAdmin(Event updated,
                                        UpdateEventAdminRequest patch,
                                        Category preСhecked, //заранее проверенная категория на наличие в БД указанная в объекте UpdateEventAdminRequest
                                        int eventId
    ) {

        String annotation = patch.getAnnotation();
        Integer category = patch.getCategory();
        String description = patch.getDescription();
        String eventDate = patch.getEventDate();
        Location location = patch.getLocation();
        Float lat = null;
        Float lon = null;
        if(location != null) {
            lat = location.getLat();
            lon = location.getLon();
        }
        Boolean paid = patch.getPaid();
        Integer participantLimit = patch.getParticipantLimit();
        Boolean requestModeration = patch.getRequestModeration();
        State state = getStateAdmin(patch.getStateAction(),eventId);
        if(!updated.getState().equals(State.PENDING)) {
            throw new ConflictException("Событие можно публиковать, только если оно в состоянии ожидания публикации!");
        }
        String title = patch.getTitle();
        return updated.toBuilder()
                .annotation(annotation == null ? updated.getAnnotation() : annotation)
                .category(category == null ? updated.getCategory() : preСhecked)
                .description(description == null ? updated.getDescription() : description)
                .eventDate(eventDate == null ? updated.getEventDate() : Util.getDate(eventDate))
                .lat(lat == null ? updated.getLat() : lat)
                .lon(lon == null ? updated.getLon() : lon)
                .paid(paid == null ? updated.isPaid() : paid)
                .participantLimit(participantLimit == null ? updated.getParticipantLimit() : participantLimit)
                .requestModeration(requestModeration == null ? updated.isRequestModeration() : requestModeration)
                .state(state)
                .title(title == null ? updated.getTitle() : title)
                .build();
    }

    private static State getStateAdmin(String stateAction, int eventId ) {
        switch (stateAction.toString()) {
            case "PUBLISH_EVENT": return State.PUBLISHED;
            case "REJECT_EVENT": return State.CANCELED;
           // case "SEND_TO_REVIEW": return State.PENDING;
           // case "CANCEL_REVIEW": return State.PENDING;
            default:
                throw  new ConflictException("Не удается определить значение # " +
                        "статуса, на изменение статуса события #",stateAction,eventId);
        }
    }

    public static Event patchEventUser(Event updated,
                                        UpdateEventUserRequest patch,
                                        Category preСhecked, //заранее проверенная категория на наличие в БД указанная в объекте UpdateEventAdminRequest
                                        int eventId
    ) {

        String annotation = patch.getAnnotation();
        Integer category = patch.getCategory();
        String description = patch.getDescription();
        String eventDate = patch.getEventDate();
        Location location = patch.getLocation();
        Float lat = null;
        Float lon = null;
        if(location != null) {
            lat = location.getLat();
            lon = location.getLon();
        }
        Boolean paid = patch.getPaid();
        Integer participantLimit = patch.getParticipantLimit();
        Boolean requestModeration = patch.getRequestModeration();
        State state = getStateUser(patch.getStateAction(),eventId);
        if(!updated.getState().equals(State.PENDING)) {
            throw new ConflictException("Событие можно публиковать, только если оно в состоянии ожидания публикации!");
        }

        String title = patch.getTitle();
        return updated.toBuilder()
                .annotation(annotation == null ? updated.getAnnotation() : annotation)
                .category(category == null ? updated.getCategory() : preСhecked)
                .description(description == null ? updated.getDescription() : description)
                .eventDate(eventDate == null ? updated.getEventDate() : Util.getDate(eventDate))
                .lat(lat == null ? updated.getLat() : lat)
                .lon(lon == null ? updated.getLon() : lon)
                .paid(paid == null ? updated.isPaid() : paid)
                .participantLimit(participantLimit == null ? updated.getParticipantLimit() : participantLimit)
                .requestModeration(requestModeration == null ? updated.isRequestModeration() : requestModeration)
                .state(state)
                .title(title == null ? updated.getTitle() : title)
                .build();
    }

    public static Map<Compilation, List<Integer>> patchCompilation(Compilation updated,
                                                                    UpdateCompilationRequest patch

    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        List<Integer> eventIds = patch.getEvents();
        if(eventIds != null) {
            try {
                json = objectMapper.writeValueAsString(eventIds);
            } catch (JsonProcessingException e) {
                throw new ConflictException("Ошибка преобразования данных при обновлении подборки # событий # ",updated,eventIds);
            }
        } else {
            json = updated.getEvents();
        }
        String title = patch.getTitle();
        Boolean pinned = patch.getPinned();
        Map<Compilation,List<Integer>> result = new HashMap<>();
        result.put(updated.toBuilder()
                .pinned(pinned == null ? updated.isPinned() : pinned)
                .events(json)
                .title(title == null ? updated.getTitle() : title)
                .build(),eventIds);
    return result;
    }

    private static State getStateUser(String stateAction, int eventId ) {
        switch (stateAction.toString()) {
            case "SEND_TO_REVIEW": return State.PENDING;
            case "CANCEL_REVIEW": return State.CANCELED;
            default:
                throw  new ConflictException("Не удается определить значение # " +
                        "статуса, на изменение статуса события #",stateAction,eventId);
        }
    }

}
