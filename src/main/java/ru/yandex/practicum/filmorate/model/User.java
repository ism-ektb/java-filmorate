package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    @Builder.Default
    private int id = 0;
    // Электронная почта автора
    private String email;
    // Логин автора
    private String login;
    // Имя Автора
    private String name;
    // ДР автора
    private LocalDate birthday;
}
