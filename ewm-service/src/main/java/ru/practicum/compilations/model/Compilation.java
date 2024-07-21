package ru.practicum.compilations.model;

import lombok.*;
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
@Table(name = "compilation", schema = "public")
public class Compilation {

    /**Идентификатор подборки*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**Список событий входящих в подборку*/
    //@OneToMany
    //@JoinColumn(name = "id_compilation")
    @Column(name = "list_events_json")
    private String events;
    /**Закреплена ли подборка на главной странице сайта*/
    @Column(name = "pinned")
    private boolean pinned;
    /**Заголовок подборки*/
    @Column(name = "title")
    private String title;
}
