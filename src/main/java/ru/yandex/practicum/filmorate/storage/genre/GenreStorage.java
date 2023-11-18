package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {

    /**
     * @return полный список жанров
     */
    List<Genre> getAll();

    /**
     * @param id жанра
     * @return возвращает объект класса Genre по порядковому номеру
     * throw  NullPointerException() если элемент в базе не найден
     */
    Genre getGenreById(Integer id);
}
