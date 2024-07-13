package ru.practicum.events.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.model.Event;

public interface EventsRepository extends JpaRepository<Event,Integer> {
}
