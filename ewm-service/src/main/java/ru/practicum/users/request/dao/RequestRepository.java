package ru.practicum.users.request.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.users.request.model.ParticipationRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest,Integer> {

    List<ParticipationRequest> findByRequesterId(int requesterId);
}
