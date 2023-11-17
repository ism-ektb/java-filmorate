package ru.yandex.practicum.filmorate.storage.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {

    /**
     * @return полный список рейтингов Mpa
     */
    List<Mpa> getAll();

    /**
     * @param id рейтинга
     * @return возвращает объект класса MPA по порядковому номеру
     * throw  NullPointerException() если элемент в базе не найден
     */
    Mpa getMpaById(Integer id);

    /**
     * @param filmId id фильма
     * @return возвращает рейтинг фильма в виде объекта класса Mpa
     */
    Mpa getMpaByFilmId(Integer filmId);
}