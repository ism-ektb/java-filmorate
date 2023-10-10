package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    private Map<Integer, User> users = new HashMap<>();
    int nextId = 1;

    @GetMapping("/users")
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        for (User user : users.values()) list.add(user);
        log.debug("Текущее количество авторов: {}", users.size());
        return list;
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        User userValidation;
        userValidation = validation(user);
        userValidation.setId(nextId++);
        users.put(userValidation.getId(), userValidation);
        log.info("добавлен автор - {}", userValidation.getName());

        return userValidation;

    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) {
        User userValidation;
        userValidation = validation(user);
        if (users.containsKey(userValidation.getId())) {
            users.replace(userValidation.getId(), userValidation);
            log.info("изменен автор - {}", userValidation.getName());
        } else {
            log.error("автор с таким Id отсутствует");
            throw new ValidationException("автор с таким Id отсутствует");
        }
        return userValidation;
    }

    private User validation(User user) {

        try {

            if (user.getEmail() == "") throw new ValidationException("поле электронной почты не должно быть пустым");
            if (!(user.getEmail().contains("@")))
                throw new ValidationException("поле электронной почты не прошло валидацию");
            if (user.getLogin() == "")
                throw new ValidationException("Логин не должен быть пустым");
            if (user.getLogin().contains(" "))
                throw new ValidationException("В логине не должно быть пробелов");
            if (user.getBirthday().isAfter(LocalDate.now()))
                throw new ValidationException("Дата рождения должна быть в прошлом");
            if (user.getName() == "" || user.getName() == null) user.setName(user.getLogin());
        } catch (ValidationException e) {
            log.error(e.toString());
            throw e;
        }

        return user;
    }

}
