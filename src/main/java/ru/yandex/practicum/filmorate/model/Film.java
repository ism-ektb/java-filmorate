package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

/**
 * класс хранящий информацию о фильме
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    private int id;
    // Название фильма
    private String name;
    // Описание фильма
    private String description;
    // Дата релиза
    private LocalDate releaseDate;
    // Продолжительность фильма
    private long duration;
    // список пользователей поставивших лайк
    private Set<Integer> like;
    // список жанров
    private Set<Genre> genres;
    // рейтинг MPA
    private Mpa mpa;

    public Set<Integer> getLike() {
        return like != null ? like : new HashSet<>();
    }

    public Set<Genre> getGenre() {
        return genres != null ? genres : new HashSet<>();
    }

    /**
     * @return преобразует экземпляр класса в карту для записи в базу данных
     */
    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name_film", name);
        values.put("description", description);
        values.put("releaseDate", releaseDate);
        values.put("duration", duration);
        values.put("mpa_id", mpa.getId());
        return values;
    }


}
