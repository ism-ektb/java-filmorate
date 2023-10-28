package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
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

    public Set<Integer> getLike(){
        return like != null ? like : new HashSet<>();
    }


}
