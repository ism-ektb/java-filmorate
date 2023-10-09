package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    int nextId = 1;


    @GetMapping("/films")
    public List<Film> findAll() {
        List<Film> list = new ArrayList<>();
        for (Film film : films.values()) list.add(film);
        log.debug("Текущее количество авторов: {}", films.size());
        return list;
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) throws ValidationException {
        validation(film);
        film.setId(nextId++);
        films.put(film.getId(), film);
        log.info("добавлен фильм - {}", film.getName());

        return film;

    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws ValidationException {
        validation(film);
        if (films.containsKey(film.getId())) {
            films.replace(film.getId(), film);
            log.info("изменен фильм - {}", film.getName());
        } else {
            log.error("Фильм с таким Id отсутствует");
            throw new ValidationException("Фильм с таким Id отсутствует");
        }
        return film;
    }

    private void validation(Film film) throws ValidationException {
        try {

            if (film.getName() == "")
                throw new ValidationException("Название фильма не должно быть пустым");
            if (film.getDescription() == "")
                throw new ValidationException("описание фильма не должно быть пустым");
            if (film.getName().length() > 200)
                throw new ValidationException("Длина названия фильма должна быть меньше 200 знаков");
            if (film.getDescription().length() > 200)
                throw new ValidationException("Длина описания фильма должна быть меньше 200 знаков");
            if (film.getDuration() <= 0)
                throw new ValidationException("Продолжительность фильма должна быть положительным числом");
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
                throw new ValidationException("Дата релиза фильма должна быть больше 1895-12-28");

        } catch (ValidationException e) {
            log.error(e.toString());
            throw e;
        }
    }

}
