package ru.practicum.events.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.InfoProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
public class CustomizedEventRepositoryImpl implements CustomizedEventRepository {


    private final EntityManager em;


    @Override
    public List<Event> searchE(String query,
                               Map<String,Object> param,
                               int from,
                               int size,
                               String sort
    ) {

        String qParticipantLimit = "and (count(pr.id) < e.participantLimit or e.participantLimit = 0)";

        String qPaid = "and e.paid = :paid "; //учет платные или бесплатные

        String qCategories = "and e.category.id in(:categories) ";

        String qText = "and UPPER(e.annotation) like UPPER(:text) ";



        String query1 = "select e from Event e " + //исчерпан лимит запросов на участие
                "join ParticipationRequest pr on e.id = pr.event.id " +
                "group by pr.id " +
                "having e.eventDate >= :rangeStart " +
                "and e.eventDate <= :rangeEnd " +
                qText +
                qCategories +
                qPaid + //учет платные или бесплатные
                qParticipantLimit +
                sort;
               // "e.eventDate >= :rangeStart " +
               // "and e.eventDate <= :rangeEnd";

        TypedQuery<Event> typedQuery = em.createQuery(query, Event.class);
        param.entrySet().stream().map(e -> typedQuery.setParameter(e.getKey(),e.getValue()));

        typedQuery.setFirstResult(from)
                .setMaxResults(size);


        return typedQuery.getResultList();
    }
}
