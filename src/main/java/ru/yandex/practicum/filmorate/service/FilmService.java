package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
/**
 * Сервисный класс для работы с экземплярами класса Film
 * если методам класса передается id фильма которого нет в хранилище,
 * то выбрасывается ошибка NullPointerException
 * валидность передаваемых экземпляров класса Film не проверяется
 */
public class FilmService {

    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    private UserStorage userStorage;
    private int nextId = 1;

    /**
     * возвращает список фильмов
     */
    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    /**
     * Добавление фильма в хранилище с присвоением ему id
     */
    public Film create(Film film) {
        film.setId(nextId++);
        return filmStorage.create(film);
    }

    /**
     * удаление фильма
     */
    public void deleteFilm(int filmId) {
        filmStorage.delete(filmId);
    }

    /**
     * Обновление данных о фильме если фильм отсутствует выбрасываем NullPointerException
     */
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    /**
     * Ищем фильма по id если фильм отсутствует выбрасываем NullPointerException
     * @throws NullPointerException если фильма с filmId нет в хранилище
     */
    public Film findFilmById(int filmId) {
        return filmStorage.findFilmById(filmId);
    }

    /**
     * Добавляем лайк фильму filmId от пользователя userId
     * @throws NullPointerException - Если фильм или автор с переданным id не найден
     */
    public void createLike(int filmId, int userId) {

        //проверяем существует ли автор
        userStorage.findUserById(userId);
        Set<Integer> likeFilmUserId = new HashSet(filmStorage.findFilmById(filmId).getLike());
        if (!(likeFilmUserId.add(userId))) {
            log.error("автор с id {} ранее добавил лайк фильму с id {}", userId, filmId);
        } else log.info("автор с id {} добавил лайк фильму с id {}", userId, filmId);
        filmStorage.findFilmById(filmId).setLike(likeFilmUserId);
    }

    /**
     * удаляем лайк фильма
     * @throws NullPointerException Если фильм или автор не найден
     */
    public void deleteLike(int filmId, int userId) {
        //проверяем существует ли автор
        userStorage.findUserById(userId);
        Set<Integer> likeFilmUserId = new HashSet(filmStorage.findFilmById(filmId).getLike());
        if (!(likeFilmUserId.remove(userId))) {
            log.error("автор с id {} ранее не добавлял лайк фильму с id {}", userId, filmId);
        } else log.info("автор с id {} удалил лайк фильму с id {}", userId, filmId);
        filmStorage.findFilmById(filmId).setLike(likeFilmUserId);
    }

    /**
     * сортируем фильмы по числу лайков и возвращаем первые count фильмов
     */
    public List<Film> sortFilmByLike(int count) {
        return filmStorage.getFilms().stream()
                .sorted((a, b) -> b.getLike().size() - (a.getLike().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
