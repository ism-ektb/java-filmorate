package ru.yandex.practicum.filmorate.storage.film.filmMpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Класс реализующий DAO интерфейс для работы с таблицей "films_mpa"
 */

@Repository
@RequiredArgsConstructor
@Slf4j
public class FilmMpaDbStorage implements FilmMpaStorage {

    private JdbcTemplate jdbcTemplate;

    @Override
    public void addMpa(Integer filmId, Integer mpaId) {
        String sqlQuery = "INSERT INTO film_mpa (id_films, id_mpa) " +
                "values (?, ?)";
        try {
            jdbcTemplate.update(sqlQuery, filmId, mpaId);
        } catch (Exception e) {
            log.error("Ошибка в добавлении рейтинга с id {} в фильму с id ()", mpaId, filmId);
        }
    }

    @Override
    public List<Integer> getListMpa(Integer filmId) {
        String sqlQuery = "SELECT id_mpa FROM film_mpa WHERE id_films = ?";
        return jdbcTemplate.queryForList(sqlQuery, Integer.class, filmId);
    }

    @Override
    public void deleteMpa(Integer filmId, Integer mpaId) {
        String sqlQuery = "DELETE FROM film_mpa WHERE (id_films = ? AND id_mpa = ?)";
        jdbcTemplate.update(sqlQuery, filmId, mpaId);
    }
}
