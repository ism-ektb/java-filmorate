package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmValidator;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@Slf4j
/**
 * контроллер фильмов, добавляет и обновляет фильмы в фильмотеке и число лайков
 */
public class FilmController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private FilmValidator filmValidator;

    /**
     * Получение общего списка фильмов из фильмотеки
     */
    @GetMapping("/films")
    public List<Film> findAll() {
        return filmService.getFilms();
    }

    /**
     * Добавление фильма в фильмотеку
     *
     * @param film
     * @return
     * @throws ValidationException - если экземпляр не прошел валидацию
     */
    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {
        try {
            filmValidator.validation(film);
        } catch (ValidationException e) {
            log.error(e.toString());
            throw e;
        }
        return filmService.create(film);
    }

    /**
     * Обновление характеристики фильма
     *
     * @param film - экземпляр класса фильм который надо обновить
     * @return
     * @throws ValidationException - если экземпляр не прошел валидацию
     *                             NullPointerException - id экземпляра отсутствует в списке
     */
    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) {
        try {
            //проверка полей экземпляра класса Film,
            //в случае отрицательного результата выбрасывается ValidationException
            filmValidator.validation(film);
        } catch (ValidationException e) {
            log.error(e.toString());
            throw e;
        }
        return filmService.update(film);
    }

    /**
     * Поиск фильма по id
     *
     * @param id - id фильма
     * @throws NullPointerException если фильм с id отсутствует
     */
    @GetMapping(value = "/films/{id}")
    public Film findFilmById(@PathVariable int id) {
        return filmService.findFilmById(id);
    }

    /**
     * добавление лайка к фильму
     *
     * @param id     - id фильма
     * @param userId - id пользователя поставившего лайк
     * @throws NullPointerException если фильм или пользователь с указанными номерами отсутствуют
     */
    @PutMapping(value = "/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.createLike(id, userId);
    }

    /**
     * удаление лайка у фильма
     *
     * @param id     - id фильма
     * @param userId - id пользователя удалившего лайк
     * @throws NullPointerException если фильм или пользователь с указанными номерами отсутствуют
     */
    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void deleteLikeFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLike(id, userId);
    }

    /**
     * возвращает фильмы в порядке популярности
     *
     * @param count - число фильмов в возвращаемом списке
     * @return возвращает экземпляры класса Film отсортированный по размеру поля like
     * @throws ValidationException если параметр count не положительный
     */
    @GetMapping(value = "/films/popular")
    public List<Film> sortFilmByLike(
            @RequestParam(value = "count", defaultValue = "10", required = false) int count) {
        if (count < 1) throw new ValidationException("число фильмов должно быть положительным");
        return filmService.sortFilmByLike(count);
    }
}
