package ru.practicum.users.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.users.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

 @Query("select u from User u where u.id in(:ids)")
 List<User> findAllByIdWithPageable(Iterable<Integer> ids, Pageable pageable);
}
