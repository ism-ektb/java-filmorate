package ru.yandex.practicum.filmorate.storage.user.friends;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс реализующий DAO интерфейс для работы с таблицей "users_friends"
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class FriendsDbStorage implements FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFriend(Integer firstFriendId, Integer secondFriendId) {
        String sqlQuery = "INSERT INTO users_friends (first_user, second_user) " +
                "values (?, ?)";
        try {
            jdbcTemplate.update(sqlQuery, firstFriendId, secondFriendId);
        } catch (Exception e) {
            log.error("Ошибка в добавлении пользователя с id {} в друзья к пользователю с id ()", secondFriendId, firstFriendId);
        }
    }

    @Override
    public List<Integer> getFriendsList(Integer id) {
        String sqlQuery = "SELECT second_user FROM users_friends WHERE first_user = ?";
        return jdbcTemplate.queryForList(sqlQuery, Integer.class, id);
    }

    @Override
    public List<User> getFriendsListOfUser(Integer id) {
        String sqlQuery = "SELECT u.* FROM users_friends AS uf " +
                "LEFT JOIN users AS u ON uf.second_user = u.id WHERE uf.first_user = ? ORDER BY u.id";
        return new ArrayList<>(jdbcTemplate.query(sqlQuery, new UserMapper(), id));
    }

    @Override
    public void deleteFriend(Integer firstFriendId, Integer secondFriendId) {
        String sqlQuery = "DELETE FROM users_friends WHERE (first_user = ? AND second_user = ?)";
        jdbcTemplate.update(sqlQuery, firstFriendId, secondFriendId);

    }

    @Override
    public List<User> commonFriends(Integer firstUserId, Integer secondUserId) {
        String sqlQuery = "SELECT u.* FROM users_friends AS uf " +
                "LEFT JOIN users AS u ON uf.second_user = u.id WHERE uf.first_user IN (?, ?)" +
                "GROUP BY u.id HAVING COUNT(uf.first_user) = 2 ORDER BY u.id";
        return new ArrayList<>(jdbcTemplate.query(sqlQuery, new UserMapper(), firstUserId, secondUserId));
    }
}
