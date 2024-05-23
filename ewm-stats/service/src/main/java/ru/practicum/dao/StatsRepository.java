package ru.practicum.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.EndpointHit;

import java.time.LocalDateTime;

public interface StatsRepository extends JpaRepository<EndpointHit,Integer> {
       // int getCountHit(LocalDateTime start,LocalDateTime end);
}
