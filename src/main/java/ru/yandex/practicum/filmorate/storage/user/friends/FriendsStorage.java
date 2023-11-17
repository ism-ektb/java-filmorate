package ru.yandex.practicum.filmorate.storage.user.friends;

import java.util.List;

/**
 * Интерфейс для работы с таблицей users_friends
 * существование передоваемых id в таблице users не проверяется
 */

public interface FriendsStorage {

    /**
     * добавление пользователя в друзья
     *
     * @param first_friend_id  инициатор дружбы
     * @param second_friend_id пользователь с которым хотят подружиться
     */
    public void addFriend(Integer firstFriendId, Integer secondFriendId);

    /**
     * @return список друзей пользователя с @param id
     */
    public List<Integer> getFriendsList(Integer id);

    /**
     * удаление пользователя @param second_friend_id из друзей пользователя @param first_friend_id
     */
    public void deleteFriend(Integer firstFriendId, Integer secondFriendId);
}
