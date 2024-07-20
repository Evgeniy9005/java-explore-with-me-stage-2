package ru.practicum.compilations.model;

import lombok.*;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.model.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "Compilation", schema = "public")
public class Compilation {

    /**Идентификатор подборки*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**Список событий входящих в подборку*/
    private List<Event> events;
    /**Закреплена ли подборка на главной странице сайта*/
    @Column(name = "pinned")
    private boolean pinned;
    /**Заголовок подборки*/
    @Column(name = "title")
    private String title;
}
