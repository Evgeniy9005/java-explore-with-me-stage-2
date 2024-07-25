package ru.practicum.users.request.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.users.request.model.ParticipationRequest;

import java.util.List;
import java.util.Map;

public interface RequestRepository extends JpaRepository<ParticipationRequest,Integer> {

    //Получение информации о заявках текущего пользователя на участие в чужих событиях
    List<ParticipationRequest> findByRequesterId(int requesterId);

    //Получение информации о запросах на участие в событии текущего пользователя
    List<ParticipationRequest> findByEventInitiatorIdAndEventId(int initiatorId, int eventId);

    /*@Modifying
    @Query("update ParticipationRequest set where ParticipationRequest.id")
    void upStatus(@Param("prIds") List<Integer> prIds);*/
    @Query("select count(pr.id) from ParticipationRequest pr where pr.event.id = ?1")
    int numberParticipants(int eventId);

    @Query("select e.id, count(pr.id) from ParticipationRequest pr " +
            "left join Event e on e.id = pr.id " +
            "group by e.id " +
            "having pr.event.id in(:participationRequestIds)")
    Map<Integer,Integer> numberEventsAndNumberParticipants(
            @Param("participationRequestIds") List<Integer> participationRequestIds
    );
}
