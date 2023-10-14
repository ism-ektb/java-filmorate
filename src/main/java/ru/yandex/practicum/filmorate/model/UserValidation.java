package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

// Класс для проверки полей объектов класса User
// если поле name пустое то возвращается объект в котором в поле name записано значение поля login
public class UserValidation {

    public User validation(User user) throws ValidationException {

        if (user.getEmail() == "") throw new ValidationException("Поле электронной почты не должно быть пустым");
        if (!(user.getEmail().contains("@")))
            throw new ValidationException("Поле электронной почты не прошло валидацию");
        if (user.getLogin() == "")
            throw new ValidationException("Логин не должен быть пустым");
        if (user.getLogin().contains(" "))
            throw new ValidationException("В логине не должно быть пробелов");
        if (user.getBirthday().isAfter(LocalDate.now()))
            throw new ValidationException("Дата рождения не должна быть в будущем");
        if (user.getName() == "" || user.getName() == null) user.setName(user.getLogin());

        return user;
    }
}
