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
                               int size
    ) {

        TypedQuery<Event> typedQuery = em.createQuery(query, Event.class);

       // typedQuery.setParameter("rangeStart",param.get("rangeStart"));
        param.entrySet().stream().forEach(e -> typedQuery.setParameter(e.getKey(),e.getValue()));

        typedQuery.setFirstResult(from)
                .setMaxResults(size);

        return typedQuery.getResultList();
    }
}
