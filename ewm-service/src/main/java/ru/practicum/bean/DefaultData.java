package ru.practicum.bean;

import org.springframework.stereotype.Component;
import ru.practicum.constants.SortEvents;
import ru.practicum.constants.State;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DefaultData {
    private List<Integer> ids = new ArrayList<>(1000);

    private List<State> stateList = new ArrayList<>();

    public List<Integer> getIdList() {
        return  IntStream.iterate(1, i -> i + 1).mapToObj(i -> i)
                .limit(1000)
                .collect(Collectors.toList());
    }

    public List<State> getStateList() {
        stateList.add(State.CANCELED);
        stateList.add(State.PUBLISHED);
        stateList.add(State.PENDING);
        return stateList;
    }

    public String getQueryEvents() {
        return "select e from Event e " + //исчерпан лимит запросов на участие //без paid
                "join ParticipationRequest pr on e.id = pr.event.id " +
                "group by pr.id " +
                "having UPPER(e.annotation) like UPPER(:text) " +
                "and e.category.id in(:categories) " +
                "and e.eventDate >= :rangeStart " +
                "and e.eventDate <= :rangeEnd " +
                "and (count(pr.id) < e.participantLimit or e.participantLimit = 0)";
    }

}
