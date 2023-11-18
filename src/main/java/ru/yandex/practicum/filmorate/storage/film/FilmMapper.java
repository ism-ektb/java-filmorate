package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Экземпляр класса преобразует строку таблицы 'films'
 * в экземпляр коасса film
 * поля mpa и genres заподяются не полностью и должны быть дополнены данными из
 * таблиц mpa, genre и film_genre
 */
public class FilmMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Film().toBuilder()
                .id(rs.getInt("id"))
                .name(rs.getString("name_film"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("releaseDate").toLocalDate())
                .duration(rs.getInt("duration"))
                .mpa(new Mpa(rs.getInt("mpa_id"), ""))
                .build();
    }
}
