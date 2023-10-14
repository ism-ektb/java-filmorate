package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmValidator;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
// контроллер фильмов, добавляет и обновляет фильмы в фильмотеке
public class FilmController {
    private Map<Integer, Film> films = new HashMap<>();
    private int nextId = 1;
    FilmValidator filmValidator = new FilmValidator();

    // Выводим общий список фильмов из фильмотеки
    @GetMapping("/films")
    public List<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return new ArrayList(films.values());
    }

    // Добавляем фильм в фильмотеку
    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) throws ValidationException {
        try {
            filmValidator.validation(film);
        } catch (ValidationException e) {
            log.error(e.toString());
            throw e;
        }
        // Присваиваем фильму Id
        film.setId(nextId++);
        films.put(film.getId(), film);
        log.debug("добавлен фильм - {}, Id = {}", film.getName(), film.getId());
        return film;

    }

    //Обновляем характеристики фильма
    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws ValidationException {
        try {
            filmValidator.validation(film);
        } catch (ValidationException e) {
            log.error(e.toString());
            throw e;
        }
        if (films.containsKey(film.getId())) {
            films.replace(film.getId(), film);
            log.debug("изменен фильм - {} Id = {}", film.getName(), film.getId());
        } else {
            log.error("Фильм с Id = {} отсутствует", film.getId());
            throw new ValidationException("Фильм с Id = " + film.getId() + "отсутствует");
        }
        return film;
    }
}
