package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@RestController
@Slf4j
public class GenreController {

    @Autowired
    private GenreService genreService;

    /**
     * Получение общего списка жанров из фильмотеки
     */
    @GetMapping("/genres")
    public List<Genre> findAll() {
        return genreService.getGenres();
    }

    /**
     * Поиск жанра по id
     *
     * @param id - id жанра
     * @throws NullPointerException если жанр с id отсутствует
     */
    @GetMapping(value = "/genres/{id}")
    public Genre findFilmById(@PathVariable int id) {
        return genreService.getGenreById(id);
    }
}
