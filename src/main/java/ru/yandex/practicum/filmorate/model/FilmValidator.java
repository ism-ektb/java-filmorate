package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import java.time.LocalDate;

public class FilmValidator {

    // Класс для проверки полей фильма
    public void validation(Film film) throws ValidationException {

        if (film.getName() == "")
            throw new ValidationException("Название фильма не должно быть пустым");
        if (film.getDescription() == "")
            throw new ValidationException("описание фильма не должно быть пустым");
        if (film.getName().length() > 200)
            throw new ValidationException("Длина названия фильма должна быть меньше 200 знаков");
        if (film.getDescription().length() > 200)
            throw new ValidationException("Длина описания фильма должна быть меньше 200 знаков");
        if (film.getDuration() <= 0)
            throw new ValidationException("Продолжительность фильма должна быть положительным числом");
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException("Дата релиза фильма должна быть больше 1895-12-28");
    }
}
