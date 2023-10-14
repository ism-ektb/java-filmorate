package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

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
}
