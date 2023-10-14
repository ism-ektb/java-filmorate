package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.model.UserValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Контроллер авторов фильмотеки, добавляет новых и обновляет старых
@RestController
@Slf4j
public class UserController {

    private Map<Integer, User> users = new HashMap<>();
    private int nextId = 1;
    private UserValidation userValidation = new UserValidation();

    // выводит список авторов
    @GetMapping("/users")
    public List<User> findAll() {
        log.debug("Текущее количество авторов: {}", users.size());
        return new ArrayList<>(users.values());
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
        //присваиваем автору Id
        validUser.setId(nextId++);
        users.put(validUser.getId(), validUser);
        log.debug("добавлен автор - {}, id = {}", validUser.getName(), validUser.getId());

        return validUser;

    }

    //обновляем данные об авторе
    @PutMapping(value = "/users")
    public User update(@RequestBody User user) {
        User validUser;
        try {
            validUser = userValidation.validation(user);
            if (users.containsKey(validUser.getId())) {
                users.replace(validUser.getId(), validUser);
                log.debug("изменен профиль автора - {}, id = {}", validUser.getName(), validUser.getId());
            } else {
                throw new ValidationException("автор с Id = " + validUser.getId() + " отсутствует");
            }
        } catch (ValidationException e) {
            log.error(e.toString());
            throw e;
        }
        return validUser;
    }


}
