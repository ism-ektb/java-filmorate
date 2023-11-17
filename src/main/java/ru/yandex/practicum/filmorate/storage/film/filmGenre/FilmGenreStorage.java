package ru.yandex.practicum.filmorate.storage.film.filmGenre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Set;

/**
 * интерфейс для работы с таблицей film_genre которая связывает id жанра и  id фильма
 * проверка на валидность по таблицам film и genre не производится
 */
public interface FilmGenreStorage {
    /**
     * добавить жанр для фильма
     */
    public void updateGenre(Integer filmId, List<Genre> genres);

    /**
     * @return Список id жанров для фильма с @param filmId
     */
    public Set<Genre> getListGenre(Integer filmId);

    /**
     * удаляет пару жанр-фильм с @param filmId и @param жанрId
     */
    public void deleteGenre(Integer filmId);
}
