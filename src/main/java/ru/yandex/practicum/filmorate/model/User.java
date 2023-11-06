package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
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
}
