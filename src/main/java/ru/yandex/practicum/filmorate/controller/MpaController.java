package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@RestController
@Slf4j
public class MpaController {

    @Autowired
    private MpaService mpaService;

    /**
     * Получение общего списка рейтингов из фильмотеки
     */
    @GetMapping("/mpa")
    public List<Mpa> findAll() {
        return mpaService.getMpa();
    }

    /**
     * Поиск рейтинга по id
     *
     * @param id - id рейтинга
     * @throws NullPointerException если рейтинг с id отсутствует
     */
    @GetMapping(value = "/mpa/{id}")
    public Mpa findFilmById(@PathVariable int id) {
        return mpaService.getMpaById(id);
    }
}
