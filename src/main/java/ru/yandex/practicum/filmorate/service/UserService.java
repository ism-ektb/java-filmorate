package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.storage.user.friends.FriendsStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
/**
 * Сервисный класс для работы с экземплярами класса User
 * если методам класса передается id автора которого нет в хранилище,
 * то выбрасывается ошибка NullPointerException
 * валидность передаваемых экземпляров класса User не проверяется
 */
public class UserService {

    @Autowired
    @Qualifier("userDbStorage")
    private UserStorage userStorage;
    @Autowired
    private FriendsStorage friendsStorage;
    private int nextId = 1;

    /**
     * Создаем автора и присваиваем ему id
     */
    public User create(User user) {
        user.setId(nextId++);
        return userStorage.create(user);
    }

    /**
     * @return список всех авторов
     */
    public List<User> getUsers() {
        return userStorage.getAllUser();
    }

    /**
     * Обновить данные об авторе
     *
     * @throws NullPointerException если автор не найден
     */
    public User update(User user) {
        return userStorage.update(user);
    }

    /**
     * удалить автора
     *
     * @throws NullPointerException если автор не найден
     */
    public boolean delete(int userId) {
        return userStorage.delete(userId);
    }

    /**
     * Находим автора по Id
     *
     * @throws NullPointerException если автор не найден
     */
    public User findUserById(int id) {
        return userStorage.findUserById(id);
    }

    /**
     * Добавить друга.
     *
     * @throws NullPointerException если хотя бы один из авторов не найден.
     */
    public void addFriend(int userId, int friendId) {

        Set<Integer> userList = new HashSet(userStorage.findUserById(userId).getFriends());
        //проверяем существует ли пользователи
        userStorage.findUserById(userId);
        userStorage.findUserById(friendId);
        friendsStorage.addFriend(userId, friendId);

        log.info("пользователь в id {} добавил в друзья пользователя с id" +
                " {} подружились", userId, friendId);
    }

    /**
     * возвращаем список друзей пользователя, если пользователя нет возвращаем NullPointerException
     */
    public List<User> getFriends(int userId) {
        //проверяем существует ли пользователи
        userStorage.findUserById(userId);
        return friendsStorage.getFriendsListOfUser(userId);
    }

    /**
     * Находим общих друзей. Если хотя бы один из авторов отсутствует возвращаем NullPointerException
     */
    public List<User> getCommonFriends(int firstUserId, int secondUserId) {

        return friendsStorage.commonFriends(firstUserId, secondUserId);
    }

    /**
     * Удаление из друзей. Если хотя бы один из авторов отсутствует выбрасываем NullPointerException
     */
    public void delete(int userId, int friendId) {
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);

        friendsStorage.deleteFriend(userId, friendId);
        log.info("авторы в id {} и {} теперь не друзья", userId, friendId);
    }
}
