package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserValidation;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

// Контроллер авторов фильмотеки, добавляет новых и обновляет старых.
// Добавляет друзей
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserValidation userValidation;

    // выводит список авторов
    @GetMapping("/users")
    public List<User> findAll() {
        return userService.getUsers();
    }

    //добавляет нового автора в случае если поле name пустое, прописывает в него логин
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

    //обновляем данные об авторе
    @PutMapping(value = "/users")
    public User update(@RequestBody User user) {
        User validUser;
        validUser = userValidation.validation(user);
        return userService.update(validUser);
    }

    // поиск автора по id
    @GetMapping(value = "/users/{id}")
    public User findUserById(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

    // взаимное добавление в друзья
    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        userService.addFriend(id, friendId);
    }

    // взаимное удаление из друзей
    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable("id") int id, @PathVariable int friendId) {
        userService.delete(id, friendId);
    }

    // возвращаем список друзей
    @GetMapping(value = "/users/{id}/friends")
    public List<User> getFriends(@PathVariable("id") int id) {
        return userService.getFriends(id);
    }

    // возвращаем список общих друзей
    @GetMapping(value = "/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Integer id, @PathVariable("otherId") Integer otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @RestControllerAdvice
    public class ErrorHandler {

        @ExceptionHandler
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public Map<String, Integer> nullPointerException(final NullPointerException e) {
            return Collections.emptyMap();
        }
    }


}
