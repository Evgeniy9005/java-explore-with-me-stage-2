package ru.practicum.users.request.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.users.request.model.ParticipationRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest,Integer> {

    //Получение информации о заявках текущего пользователя на участие в чужих событиях
    List<ParticipationRequest> findByRequesterId(int requesterId);

    //Получение информации о запросах на участие в событии текущего пользователя
    List<ParticipationRequest> findByEventInitiatorIdAndEventId(int initiatorId, int eventId);
}
