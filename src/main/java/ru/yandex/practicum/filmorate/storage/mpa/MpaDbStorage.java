package ru.yandex.practicum.filmorate.storage.mpa;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
/**
 * Класс реализующий DAO интерфейс для работы с таблицей "mpa"
 */
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAll() {
        return jdbcTemplate.query("SELECT * FROM mpa ORDER BY id", new MpaMapper());
    }

    @Override
    public Mpa getMpaById(Integer id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM mpa WHERE id = ?", new MpaMapper(), id);
        } catch (Exception e) {
            log.error("рейтинг mpa с id {} не найден", id);
            throw new NullPointerException();
        }
    }

    @Override
    public Mpa getMpaByFilmId(Integer filmId) {
        try {
            return jdbcTemplate.queryForObject("SELECT m.* " +
                    "FROM films AS f INNER JOIN mpa AS m ON f.mpa_id = m.id" +
                    " WHERE f.id = ?", new MpaMapper(), filmId);
        } catch (Exception e) {
            log.error("рейтинг для фильма с id {} не найден", filmId);
            throw new NullPointerException();
        }
    }
}
