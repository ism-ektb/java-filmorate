package ru.yandex.practicum.filmorate.storage.user.friends;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public void addFriend(Integer first_friend_id, Integer second_friend_id) {
        String sqlQuery = "INSERT INTO users_friends (first_user, second_user) " +
                "values (?, ?)";
        try {
            jdbcTemplate.update(sqlQuery, first_friend_id, second_friend_id);
        }catch (Exception e){
            log.error("Ошибка в добавлении пользователя с id {} в друзья к пользователю с id ()"
            , second_friend_id, first_friend_id);
        }
    }

    @Override
    public List<Integer> getFriendsList(Integer id) {
        String sqlQuery = "SELECT second_user FROM users_friends WHERE first_user = ?";
        return jdbcTemplate.queryForList(sqlQuery, Integer.class, id);
    }

    @Override
    public void deleteFriend(Integer first_friend_id, Integer second_friend_id) {
        String sqlQuery = "DELETE FROM users_friends WHERE (first_user = ? AND second_user = ?)";
        jdbcTemplate.update(sqlQuery, first_friend_id, second_friend_id);

    }
}
