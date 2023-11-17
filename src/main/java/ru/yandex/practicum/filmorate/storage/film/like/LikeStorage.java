package ru.yandex.practicum.filmorate.storage.film.like;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

/**
 * интерфейс для работы с таблицей like которая связывает id
 * пользователя поставившего like и id фильма
 * проверка на валидность по таблицам films и users не производится
 */
public interface LikeStorage {
    /**
     * добавить лайк для фильма
     */
    public void addLike(Integer filmId, Integer userId);

    /**
     * @return Список id пользователей поставивших лайк за фильм с @param filmId
     */
    public List<Integer> getListLike(Integer filmId);

    /**
     * удаляет пару лайк-фильм с @param filmId и @param userId
     */
    public void deleteLike(Integer filmId, Integer userId);

    /**
     * возвращает список фильмов отсортированный по количеству лайков
     */
    public List<Film> sortByLike();
}
