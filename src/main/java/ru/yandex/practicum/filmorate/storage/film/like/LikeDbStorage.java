package ru.yandex.practicum.filmorate.storage.film.like;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmMapper;

import java.util.List;

/**
 * Класс реализующий DAO интерфейс для работы с таблицей "likes"
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(Integer filmId, Integer userId) {
        String sqlQuery = "INSERT INTO likes (films_id, users_id) " +
                "values (?, ?)";
        try {
            jdbcTemplate.update(sqlQuery, filmId, userId);
        } catch (Exception e) {
            log.error("Ошибка в добавлении лайка от пользователя с id {} в фильму с id ()", userId, filmId);
        }
    }

    @Override
    public List<Integer> getListLike(Integer filmId) {
        String sqlQuery = "SELECT users_id FROM likes WHERE films_id = ?";
        return jdbcTemplate.queryForList(sqlQuery, Integer.class, filmId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        String sqlQuery = "DELETE FROM likes WHERE (films_id = ? AND users_id = ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public List<Film> sortByLike() {
        String sqlQuery = "SELECT f.*, COUNT(l.users_id) AS likes FROM films AS f LEFT JOIN likes AS l " +
                "ON f.id = l.films_id GROUP BY f.id ORDER BY likes DESC ";
        return jdbcTemplate.query(sqlQuery, new FilmMapper());
    }
}
