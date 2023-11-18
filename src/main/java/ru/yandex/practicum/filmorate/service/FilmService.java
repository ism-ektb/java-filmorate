package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.filmGenre.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.film.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @Qualifier("filmDbStorage")
    private FilmStorage filmStorage;
    @Autowired
    @Qualifier("userDbStorage")
    private UserStorage userStorage;
    @Autowired
    private FilmGenreStorage filmGenreStorage;
    @Autowired
    private LikeStorage likeStorage;

    @Autowired
    private MpaStorage mpaStorage;

    private int nextId = 1;

    /**
     * возвращает список фильмов
     */
    public List<Film> getFilms() {

        List<Film> list = filmStorage.getFilms();
        List<Film> newList = new ArrayList<>();
        for (Film film : list) {
            int id = film.getId();
            //добавляем данные из дополнительных таблиц
            film.setGenres(filmGenreStorage.getListGenre(id));
            film.setMpa(mpaStorage.getMpaByFilmId(id));
            newList.add(film);
        }
        return newList;
    }

    /**
     * Добавление фильма в хранилище с присвоением ему id
     */
    public Film create(Film film) {
        Film newFilm = filmStorage.create(film);
        // заполняем все поля объектов класса genre
        filmGenreStorage.updateGenre(newFilm.getId(), new ArrayList<>(film.getGenre()));
        return newFilm;
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

        Film newFilm = filmStorage.update(film);
        // заполняем дополнительную таблицу с парами фильм-жанр
        filmGenreStorage.updateGenre(film.getId(), new ArrayList<>(film.getGenre()));
        // заполняем все поля объектов класса genre если они не были заполненны
        newFilm.setGenres(new HashSet<>(filmGenreStorage.getListGenre(film.getId())));
        return newFilm;
    }

    /**
     * Ищем фильма по id если фильм отсутствует выбрасываем NullPointerException
     *
     * @throws NullPointerException если фильма с filmId нет в хранилище
     */
    public Film findFilmById(int filmId) {

        Film film = filmStorage.findFilmById(filmId);
        int id = film.getId();
        // заполняем поля мpa и genre POJO объекта film
        film.setMpa(mpaStorage.getMpaByFilmId(id));
        film.setGenres(new HashSet<>(filmGenreStorage.getListGenre(filmId)));

        return film;
    }

    /**
     * Добавляем лайк фильму filmId от пользователя userId
     *
     * @throws NullPointerException - Если фильм или автор с переданным id не найден
     */
    public void createLike(int filmId, int userId) {

        //проверяем существует ли автор
        userStorage.findUserById(userId);
        filmStorage.findFilmById(filmId);
        likeStorage.addLike(filmId, userId);

    }

    /**
     * удаляем лайк фильма
     *
     * @throws NullPointerException Если фильм или автор не найден
     */
    public void deleteLike(int filmId, int userId) {
        //проверяем существует ли автор
        userStorage.findUserById(userId);
        likeStorage.deleteLike(filmId, userId);
    }

    /**
     * сортируем фильмы по числу лайков и возвращаем первые count фильмов
     */
    public List<Film> sortFilmByLike(int count) {
        return likeStorage.sortByLike().stream()
                .map(f -> {
                    // заполняем поля мpa и genre POJO объекта film
                    int id = f.getId();
                    f.setMpa(mpaStorage.getMpaByFilmId(id));
                    f.setGenres(new HashSet<>(filmGenreStorage.getListGenre(id)));
                    return f;
                })
                .limit(count)
                .collect(Collectors.toList());

    }
}
