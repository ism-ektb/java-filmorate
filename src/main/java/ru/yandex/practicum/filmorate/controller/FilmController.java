package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmValidator;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
// контроллер фильмов, добавляет и обновляет фильмы в фильмотеке
public class FilmController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmValidator filmValidator;

    // Выводим общий список фильмов из фильмотеки
    @GetMapping("/films")
    public List<Film> findAll() throws ValidationException {
        return filmService.getFilms();
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
        return filmService.create(film);
    }

    //Обновляем характеристики фильма
    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws ValidationException {
        try {
            //проверяем поля фильма, если список лайков null, подставляем пустой список лайков
            filmValidator.validation(film);
        } catch (ValidationException e) {
            log.error(e.toString());
            throw e;
        }
        return filmService.update(film);
    }

    //Ищем фильм по id
    @GetMapping(value = "/films/{id}")
    public Film findFilmById(@PathVariable int id) {
        return filmService.findFilmById(id);
    }

    //добавляем лайк фильму
    @PutMapping(value = "/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.createLike(id, userId);
    }

    //удаляем лайк у фильма
    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void deleteLikeFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    // возвращаем фильмы в порядке популярности
    @GetMapping(value = "/films/popular")
    public List<Film> sortFilmByLike(
            @RequestParam(value = "count", defaultValue = "10", required = false) int count) {
        if (count < 1) throw new ValidationException("число фильмов должно быть положительным");
        return filmService.sortFilmByLike(count);
    }

    @RestControllerAdvice
    public class ErrorHandler {

        @ExceptionHandler
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public Map<String, Integer> nullPointerException(final NullPointerException e) {
            return Collections.emptyMap();
        }
    }
}
