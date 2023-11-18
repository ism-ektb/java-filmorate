package ru.yandex.practicum.filmorate.storage.user.friends;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

/**
 * Интерфейс для работы с таблицей users_friends
 * существование передоваемых id в таблице users не проверяется
 */

public interface FriendsStorage {

    /**
     * добавление пользователя в друзья
     *
     * @param firstFriendId  инициатор дружбы
     * @param secondFriendId пользователь с которым хотят подружиться
     */
    public void addFriend(Integer firstFriendId, Integer secondFriendId);

    /**
     * @return список друзей пользователя с @param id
     */
    public List<Integer> getFriendsList(Integer id);

    /**
     * @return список друзей пользователя с @param id состоящий из POJO User
     */
    public List<User> getFriendsListOfUser(Integer id);

    /**
     * удаление пользователя @param second_friend_id из друзей пользователя @param first_friend_id
     */
    public void deleteFriend(Integer firstFriendId, Integer secondFriendId);

    /**
     * Возвращает список общих друзей
     *
     * @param firstUserId
     * @param secondUserId
     * @return список POJO User
     */
    public List<User> commonFriends(Integer firstUserId, Integer secondUserId);
}
