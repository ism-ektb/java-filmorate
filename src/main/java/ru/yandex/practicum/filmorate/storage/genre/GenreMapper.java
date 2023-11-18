package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Экземпляр класса преобразует строку таблицы 'genre'
 * в экземпляр коасса Genre
 */
public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Genre().toBuilder()
                .id(rs.getInt("id"))
                .name(rs.getString("name_genre"))
                .build();
    }
}