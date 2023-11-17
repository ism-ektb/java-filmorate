package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * класс для ханения данных о пользователе
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    // Электронная почта автора
    private String email;
    // Логин автора
    private String login;
    // Имя Автора
    private String name;
    // ДР автора
    private LocalDate birthday;
    // список id друзей
    private Set<Integer> friends;

    public Set<Integer> getFriends() {
        return friends != null ? friends : new HashSet<>();
    }

    /**
     * Метод преобразует объект в карту для записи в базу данных
     */
    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("birthday", birthday);
        values.put("name_user", name);
        return values;
    }
}
