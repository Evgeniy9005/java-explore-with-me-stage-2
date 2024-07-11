package ru.practicum.users.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.users.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

 //   List<User> findAllById(Iterable<Integer> ids, Pageable pageable);
}
