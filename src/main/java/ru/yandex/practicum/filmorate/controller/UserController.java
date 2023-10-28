package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserValidation;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

/**
 * Контроллер авторов фильмотеки, добавляет новых и обновляет старых.
 * Добавляет друзей
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserValidation userValidation;

    /**
     * вывод списка авторов
     */
    @GetMapping("/users")
    public List<User> findAll() {
        return userService.getUsers();
    }

    /**
     * добавление нового автора в случае если поле name пустое, прописывает ему значение поля логин
     */
    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        User validUser;
        try {
            validUser = userValidation.validation(user);
        } catch (ValidationException e) {
            log.error(e.toString());
            throw e;
        }
        return userService.create(validUser);
    }

    /**
     * обновление данных об авторе
     *
     * @throws ValidationException если передаваемый экземпляр не прошел валидацию
     *                             NullPointException если автора с таким id не существует
     */
    @PutMapping(value = "/users")
    public User update(@RequestBody User user) {
        User validUser;
        validUser = userValidation.validation(user);
        return userService.update(validUser);
    }

    /**
     * поиск автора по id
     *
     * @return экземпляр класса User
     * @throws NullPointerException если автора с таким id не существует
     */
    @GetMapping(value = "/users/{id}")
    public User findUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

    /**
     * взаимное добавление в друзья
     *
     * @throws NullPointerException если авторы с id id или friendId не существует
     */
    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        userService.addFriend(id, friendId);
    }

    /**
     * взаимное удаление из друзей
     *
     * @throws NullPointerException если авторы с id id или friendId не существует
     */
    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable("id") int id, @PathVariable int friendId) {
        userService.delete(id, friendId);
    }

    /**
     * возвращает список друзей
     *
     * @throws NullPointerException если автора с id не существует
     */
    @GetMapping(value = "/users/{id}/friends")
    public List<User> getFriends(@PathVariable("id") int id) {
        return userService.getFriends(id);
    }

    /**
     * возвращает список общих друзей
     *
     * @throws NullPointerException если авторы с id id или otherId не существуют
     */
    @GetMapping(value = "/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
