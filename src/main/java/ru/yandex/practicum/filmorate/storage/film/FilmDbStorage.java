package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.filmGenre.FilmGenreStorage;

import java.util.List;

/**
 * Класс реализующий DAO интерфейс для работы с таблицей "films"
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private FilmGenreStorage filmGenreStorage;

    @Override
    public Film create(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("id");
        Integer id = simpleJdbcInsert.executeAndReturnKey(film.toMap()).intValue();
        if (id == null) {
            log.error("Объект {} не сохранен", film.toString());
            throw new NullPointerException();
        }
        film.setId(id);
        return film;
    }

    @Override
    public void delete(int id) {
        String sqlQuery = "DELETE FROM films WHERE id = ?";
        if (jdbcTemplate.update(sqlQuery, id) == 0)
            log.error("пользователь с id {} не сушествовал", id);
    }

    @Override
    public Film update(Film film) {

        // проверяем существует ли пользователь с таким Id
        findFilmById(film.getId());
        String sqlQuery = "UPDATE films SET name_film = ?, description = ?, " +
                "releaseDate = ?, duration = ?, mpa_id = ? where id = ?";
        jdbcTemplate.update(
                sqlQuery,
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                film.getMpa().getId(), film.getId());
        return film;
    }

    @Override
    public List<Film> getFilms() {
        String sqlQuery = "SELECT * FROM films";
        return jdbcTemplate.query(sqlQuery, new FilmMapper());
    }

    @Override
    public Film findFilmById(int id) {

        String sqlQuery = "SELECT * FROM films WHERE id = ?";
        try {
            Film film = jdbcTemplate.queryForObject(sqlQuery, new FilmMapper(), id);
            return film;
        } catch (Exception e) {
            log.error("фильм с id {} не найден", id);
            throw new NullPointerException();
        }

    }
}
