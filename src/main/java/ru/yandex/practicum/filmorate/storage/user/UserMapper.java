package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Экземпляр класса преобразует строку таблицы 'user'
 * в экземпляр коасса User
 * поле friends заполняется в сервисном классе
 */
public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User().toBuilder()
                .id(rs.getInt("id"))
                .login(rs.getString("login"))
                .name(rs.getString("name_user"))
                .email(rs.getString("email"))
                .birthday(rs.getDate("birthday") != null ?
                        rs.getDate("birthday").toLocalDate() : null)
                .build();
    }
}
