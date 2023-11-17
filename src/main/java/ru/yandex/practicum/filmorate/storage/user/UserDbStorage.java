package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

/**
 * Класс реализующий DAO интерфейс для работы с таблицей "users"
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        Integer id = simpleJdbcInsert.executeAndReturnKey(user.toMap()).intValue();
        if (id == null) {
            log.error("Объект {} не сохранен", user.toString());
            throw new NullPointerException();
        }
        user.setId(id);
        return user;

    }

    @Override
    public boolean delete(int id) {
        String sqlQuery = "DELETE FROM users WHERE id = ?";
        if (jdbcTemplate.update(sqlQuery, id) > 0) return true;
        else {
            log.error("пользователь с id {} не сушествовал", id);
            return false;
        }
    }

    @Override
    public User update(User user) {
        // проверяем существует ли пользователь с таким Id
        findUserById(user.getId());

        String sqlQuery = "UPDATE users SET name_user = ?, login = ?, birthday = ?" +
                ", email = ? where id = ?";

        jdbcTemplate.update(
                sqlQuery,
                user.getName(), user.getLogin(), user.getBirthday(), user.getEmail(), user.getId());
        return user;
    }

    @Override
    public List<User> getAllUser() {
        String sqlQuery = "SELECT * FROM users";
        return jdbcTemplate.query(sqlQuery, new UserMapper());
    }

    @Override
    public User findUserById(int userId) {
        String sqlQuery = "SELECT * FROM users where id = ?";

        try {
            return jdbcTemplate.queryForObject(sqlQuery, new UserMapper(), userId);
        } catch (Exception e) {
            log.error("пользователь с id {} не найден", userId);
            throw new NullPointerException();
        }
    }
}
