package ru.practicum.events.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.constants.State;
import ru.practicum.events.model.Event;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventsRepository extends JpaRepository<Event,Integer> ,CustomizedEventRepository {


   @Query("select e from Event e where e.initiator.id in(:users) " +
           "and e.state in(:states) " +
           "and e.category.id in(:categories) " +
           "and e.eventDate >= :rangeStart " +
           "and e.eventDate <= :rangeEnd")
   List<Event> getEvents(@Param("users") List<Integer> users,
                         @Param("states") List<State> states,
                         @Param("categories") List<Integer> categories,
                         @Param("rangeStart") LocalDateTime rangeStart,//дата и время не раньше которых должно произойти событие
                         @Param("rangeEnd") LocalDateTime rangeEnd,//дата и время не позже которых должно произойти событие
                         Pageable pageable);


   List<Event> findByInitiatorId(int userId,Pageable pageable);

   Optional<Event> findByInitiatorIdAndId(int userId, int eventId);

   Optional<Event> findByIdAndState(int eventId,State state); //для публичного эндпоинта можно вернуть только опубликованные события

  /* @Query("select e from Event e where UPPER(e.annotation) like UPPER(:text) " +
           "and e.category.id in(:categories) " +
           "and e.paid = :paid " + //учет платные или бесплатные
           "and e.eventDate >= :rangeStart " +
           "and e.eventDate <= :rangeEnd")
   List<Event> searchEvents(@Param("text") String text,
                            @Param("categories") List<Integer> categories,
                            @Param("paid") boolean paid,
                            @Param("rangeStart") LocalDateTime rangeStart,
                            @Param("rangeEnd") LocalDateTime rangeEnd,
                            Pageable pageable);*/

  /* @Query("select e from Event e " + //исчерпан лимит запросов на участие
           "join ParticipationRequest pr on e.id = pr.event.id " +
           "group by pr.id " +
           "having UPPER(e.annotation) like UPPER(:text) " +
           "and e.category.id in(:categories) " +
           "and e.paid = :paid " + //учет платные или бесплатные
           "and e.eventDate >= :rangeStart " +
           "and e.eventDate <= :rangeEnd " +
           "and (count(pr.id) < e.participantLimit or e.participantLimit = 0)")
   List<Event> searchEventsLimitNumberRequests(@Param("text") String text,
                               @Param("categories") List<Integer> categories,
                               @Param("paid") boolean paid,
                               @Param("rangeStart") LocalDateTime rangeStart,
                               @Param("rangeEnd") LocalDateTime rangeEnd,
                               Pageable pageable);*/


/*   @Query("select e from Event e where UPPER(e.annotation) like UPPER(:text) " + //без paid
           "and e.category.id in(:categories) " +
           "and e.eventDate >= :rangeStart " +
           "and e.eventDate <= :rangeEnd")
   List<Event> searchEvents(@Param("text") String text,
                            @Param("categories") List<Integer> categories,
                            @Param("rangeStart") LocalDateTime rangeStart,
                            @Param("rangeEnd") LocalDateTime rangeEnd,
                            Pageable pageable);*/

  /* @Query("select e from Event e " + //исчерпан лимит запросов на участие //без paid
           "join ParticipationRequest pr on e.id = pr.event.id " +
           "group by pr.id " +
           "having UPPER(e.annotation) like UPPER(:text) " +
           "and e.category.id in(:categories) " +
           "and e.eventDate >= :rangeStart " +
           "and e.eventDate <= :rangeEnd " +
           "and (count(pr.id) < e.participantLimit or e.participantLimit = 0)")
   List<Event> searchEventsLimitNumberRequests(@Param("text") String text,
                                               @Param("categories") List<Integer> categories,
                                               @Param("rangeStart") LocalDateTime rangeStart,
                                               @Param("rangeEnd") LocalDateTime rangeEnd,
                                               Pageable pageable);*/



}
