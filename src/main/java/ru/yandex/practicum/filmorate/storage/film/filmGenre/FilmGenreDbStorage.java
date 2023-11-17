package ru.yandex.practicum.filmorate.storage.film.filmGenre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс реализующий DAO интерфейс для работы с таблицей "film_genre"
 */

@Repository
@RequiredArgsConstructor
@Slf4j
public class FilmGenreDbStorage implements FilmGenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void updateGenre(Integer filmId, List<Genre> listGenre) {
        deleteGenre(filmId);
        try {
            for (Genre genre : listGenre) {
                String sqlQuery = "INSERT INTO film_genre (id_films, id_genre) " +
                        "values (?, ?)";
                jdbcTemplate.update(sqlQuery, filmId, genre.getId());
            }
        } catch (Exception e) {
            log.error("Ошибка сохранения жанров фильма с id ()", filmId);
        }
    }

    @Override
    public Set<Genre> getListGenre(Integer filmId) {
        String sqlQuery = "SELECT f.id_genre AS id, g.name_genre AS name_genre" +
                " FROM film_genre AS f LEFT JOIN genre AS g " +
                "ON f.id_genre = g.id " +
                "WHERE f.id_films = ? ORDER BY id";
        Set<Genre> genres = new HashSet<>(jdbcTemplate.query(sqlQuery, new GenreMapper(), filmId));
        return genres;
    }

    @Override
    public void deleteGenre(Integer filmId) {
        String sqlQuery = "DELETE FROM film_genre WHERE id_films = ? ";
        jdbcTemplate.update(sqlQuery, filmId);
    }
}
