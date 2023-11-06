package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
/**
 * Сервисный класс для хранения экземпляров класса User в памяти компьютера
 * если экземпляр с необходимым значением поля id отсутствует, то
 * выбрасывается исключение NullPointerException
 * поддерживается интерфейс UserStorage
 */
public class ImMemoryUserStorage implements UserStorage {
    private Map<Integer, User> users = new HashMap<>();

    @Override
    public User create(User user) {
        users.put(user.getId(), user);
        log.debug("автор с login {} id {} сохранен", user.getLogin(), user.getId());
        return user;
    }

    @Override
    public boolean delete(int id) {
        if (users.remove(id) != null) {
            log.debug("автор с id {} удален", id);
            return true;
        } else {
            log.error("автор с id {} отсутствует", id);
            return false;
        }
    }

    @Override
    public User update(User user) throws NullPointerException {
        if (users.containsKey(user.getId())) {
            users.replace(user.getId(), user);
            log.debug("данные автора с id {} изменены", user.getId());
            return user;
        } else {
            log.error("автор с id {} отсутствует", user.getId());
            throw new NullPointerException();
        }
    }

    @Override
    public List<User> getAllUser() {
        return new ArrayList(users.values());
    }

    @Override
    public User findUserById(int userId) throws NullPointerException {
        if (users.containsKey(userId)) {
            log.info("найден автор с id {}", userId);
            return users.get(userId);
        } else {
            log.info("автор с id {} не найден", userId);
            throw new NullPointerException();
        }
    }
}
