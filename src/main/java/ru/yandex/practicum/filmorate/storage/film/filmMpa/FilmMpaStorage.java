package ru.yandex.practicum.filmorate.storage.film.filmMpa;

import java.util.List;

/**
 * интерфейс для работы с таблицей film_mpa которая связывает id рейтинга и  id фильма
 * проверка на валидность по таблицам film и mpa не производится
 */
public interface FilmMpaStorage {
    /**
     * добавить рейтинг для фильма
     */
    public void addMpa(Integer filmId, Integer mpaId);

    /**
     * @return Список id рейтингов МРА для фильма с @param filmId
     */
    public List<Integer> getListMpa(Integer filmId);

    /**
     * удаляет пару рейтинг-фильм с @param filmId и @param mpaId
     */
    public void deleteMpa(Integer filmId, Integer mpaId);
}
