package ru.practicum.events.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.events.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
public class CustomizedEventRepositoryImpl implements CustomizedEventRepository {


    private final EntityManager em;


    @Override
    public List<Event> searchE(String query, String text,
                              List<Integer> categories,
                              Boolean paid,
                              LocalDateTime rangeStart,
                              LocalDateTime rangeEnd,
                              Pageable pageable) {

        String qParticipantLimit = "and (count(pr.id) < e.participantLimit or e.participantLimit = 0)";

        String qPaid = "and e.paid = :paid "; //учет платные или бесплатные

        String qCategories = "and e.category.id in(:categories) ";

        String qText = "and UPPER(e.annotation) like UPPER(:text) ";



        String query = "select e from Event e " + //исчерпан лимит запросов на участие
                "join ParticipationRequest pr on e.id = pr.event.id " +
                "group by pr.id " +
                "having e.eventDate >= :rangeStart " +
                "and e.eventDate <= :rangeEnd " +
                qText +
                qCategories +
                qPaid + //учет платные или бесплатные
                qParticipantLimit;
               // "e.eventDate >= :rangeStart " +
               // "and e.eventDate <= :rangeEnd";

        TypedQuery<Event> typedQuery = em.createQuery(query, Event.class);

        if (text == null) {
            typedQuery.setParameter("text", text);
        }

        if(categories == null) {
            typedQuery.setParameter("categories", categories);
        }

        if(paid == null) {
            typedQuery.setParameter("paid", paid);
        }

        return typedQuery.getResultList();
    }
}
