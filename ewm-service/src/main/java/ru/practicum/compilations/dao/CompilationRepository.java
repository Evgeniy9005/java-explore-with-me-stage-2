package ru.practicum.compilations.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilations.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation,Integer> {
    List<Compilation> findByPinned(boolean pinned, Pageable pageable);
}
