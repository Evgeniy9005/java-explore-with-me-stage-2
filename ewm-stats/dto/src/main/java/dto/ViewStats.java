package dto;

import lombok.*;

import java.util.Objects;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ViewStats {
    private String app;
    private String uri;
    private long hits;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ViewStats viewStats = (ViewStats) o;
        return hits == viewStats.hits && Objects.equals(app, viewStats.app) && Objects.equals(uri, viewStats.uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app, uri, hits);
    }
}
