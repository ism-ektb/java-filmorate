package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

/**
 * Интерфейс хранилища экземпляров класса User
 * если экземпляр с необходимым значением поля id отсутствует, то
 * выбрасывается исключение NullPointerException
 */
public interface UserStorage {

    //создание автора
    User create(User user);

    //удаление автора
    boolean delete(int id);

    //модификация данных автора если пользователь не найден выбрасываем NullPointerException
    User update(User user);

    //список всех авторов
    List<User> getAllUser();

    //найти пользователя по id если пользователь не найден выбрасываем NullPointerException
    User findUserById(int userId);
}
