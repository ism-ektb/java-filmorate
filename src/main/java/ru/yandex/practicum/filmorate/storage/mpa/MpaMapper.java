package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Экземпляр класса преобразует строку таблицы 'mpa'
 * в экземпляр коасса Mpa
 */
public class MpaMapper implements RowMapper<Mpa> {

    @Override
    public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Mpa().toBuilder()
                .id(rs.getInt("id"))
                .name(rs.getString("name_mpa"))
                .build();
    }
}