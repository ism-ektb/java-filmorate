package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
/**
 * Класс реализующий DAO интерфейс для работы с таблицей "genre"
 */
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAll() {

        return jdbcTemplate.query("SELECT * FROM genre ORDER BY id", new GenreMapper());
    }

    @Override
    public Genre getGenreById(Integer id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM genre WHERE id = ?", new GenreMapper(), id);
        } catch (Exception e) {
            log.error("Жанр с id {} отсутствует в базе данных", id);
            throw new NullPointerException();
        }
    }
}
