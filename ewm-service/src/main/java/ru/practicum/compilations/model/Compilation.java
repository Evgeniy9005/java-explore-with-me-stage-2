package ru.practicum.compilations.model;

import lombok.*;
import ru.practicum.events.model.Event;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compilation that = (Compilation) o;
        return id == that.id && pinned == that.pinned && Objects.equals(events, that.events) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, events, pinned, title);
    }
}
