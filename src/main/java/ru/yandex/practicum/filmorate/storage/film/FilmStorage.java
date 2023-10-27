package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    //создание объекта
    Film create(Film film);

    //удаление фильма
    void delete(int id);

    //модификация данных о фильме если фильма нет выбрасываем NullPointerException
    Film update(Film film);

    //Список всех фильмов
    List<Film> getFilms();

    //получение фильма по ID если фильма нет выбрасываем NullPointerException
    Film findFilmById(int id);
}

