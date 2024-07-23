package ru.practicum.compilations;

import ru.practicum.compilations.dto.CompilationDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CompilationService {

    List<CompilationDto> getCompilations(Boolean pinned,
                                                int from,
                                                int size,
                                                HttpServletRequest request
    );

    CompilationDto getCompilation(int compId, HttpServletRequest request);
}
