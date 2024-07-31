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

public interface EventsRepository extends JpaRepository<Event,Integer> {


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

   /*default String rewrite(String query, List<Integer> users,List<State> states,List<Integer> categories) {
      String newQuery = "";
      if(users == null) {
         query = query.replaceAll(":users","select u from User");
      }

      if(states == null) {
        query = query.replaceAll(":users","select u from User");
      }

      if(categories == null) {

      }

      return query;
   }*/

   List<Event> findByInitiatorId(int userId,Pageable pageable);

   @Query("select e from Event e where UPPER(e.annotation) like UPPER(:text) " +
           "and e.category.id in(:categories) " +
           "and e.paid = :paid " +
           "and e.eventDate >= :rangeStart " +
           "and e.eventDate <= :rangeEnd")
   List<Event> searchEvents(@Param("text") String text,
                            @Param("categories") List<Integer> categories,
                            @Param("paid") boolean paid,
                            @Param("rangeStart") LocalDateTime rangeStart,
                            @Param("rangeEnd") LocalDateTime rangeEnd,
                            Pageable pageable);

   @Query("select e from Event e " +
           "join ParticipationRequest pr on e.id = pr.event.id " +
           "group by pr.id " +
           "having UPPER(e.annotation) like UPPER(:text) " +
           "and e.category.id in(:categories) " +
           "and e.paid = :paid " +
           "and e.eventDate >= :rangeStart " +
           "and e.eventDate <= :rangeEnd " +
           "and (count(pr.id) < e.participantLimit or e.participantLimit = 0)")
   List<Event> searchEventsLimitNumberRequests(@Param("text") String text,
                               @Param("categories") List<Integer> categories,
                               @Param("paid") boolean paid,
                               @Param("rangeStart") LocalDateTime rangeStart,
                               @Param("rangeEnd") LocalDateTime rangeEnd,
                               Pageable pageable);

   Optional<Event> findByInitiatorIdAndId(int userId, int eventId);
}
