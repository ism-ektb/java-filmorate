package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

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
    private UserStorage userStorage;
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
        //проверяем существует ли автор с friendId
        userStorage.findUserById(friendId);
        userList.add(friendId);
        userStorage.findUserById(userId).setFriends(userList);
        userList = new HashSet(userStorage.findUserById(friendId).getFriends());
        userList.add(userId);
        userStorage.findUserById(friendId).setFriends(userList);
        log.info("авторы в id {} и {} подружились", userId, friendId);
    }

    /**
     * возвращаем список друзей пользователя, если пользователя нет возвращаем NullPointerException
     */
    public List<User> getFriends(int userId) {
        return userStorage.findUserById(userId).getFriends().stream()
                .map(e -> userStorage.findUserById(e))
                .collect(Collectors.toList());
    }

    /**
     * Находим общих друзей. Если хотя бы один из авторов отсутствует возвращаем NullPointerException
     */
    public List<User> getCommonFriends(int userId, int friendId) {
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);
        return friend.getFriends().stream()
                .filter(user.getFriends()::contains)
                .map(e -> userStorage.findUserById(e))
                .collect(Collectors.toList());
    }

    /**
     * Удаление из друзей. Если хотя бы один из авторов отсутствует выбрасываем NullPointerException
     */
    public void delete(int userId, int friendId) {
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);

        Set<Integer> userList = new HashSet(user.getFriends());
        userList.remove(friendId);
        user.setFriends(userList);
        userList = new HashSet(friend.getFriends());
        userList.remove(userId);
        friend.setFriends(userList);
        userStorage.update(user);
        userStorage.update(friend);
        log.debug("авторы в id {} и {} теперь не друзья", userId, friendId);
    }
}
