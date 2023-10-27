package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {

    @Autowired
    private FilmStorage filmStorage;
    @Autowired
    private UserStorage userStorage;
    private int nextId = 1;

    //возвращаем список фильмов
    public List<Film> getFilms(){
        return filmStorage.getFilms();
    }

    // Добавляем фильм и присваиваем id
    public Film create(Film film){
        film.setId(nextId++);
        return filmStorage.create(film);
    }

    //Удаляем фильм
    public void deleteFilm(int filmId){
        filmStorage.delete(filmId);
    }

    //Обновляем данные о фильме если фильм отсутствует выбрасываем NullPointerException
    public Film update(Film film) throws NullPointerException{
        return filmStorage.update(film);
    }

    //Ищем фильм по id если фильм отсутствует выбрасываем NullPointerException
    public Film findFilmById(int filmId) throws NullPointerException{
        return filmStorage.findFilmById(filmId);
    }

    //Добавляем лайк фильму
    //Если фильм или автор не найден выбрасываем NullPointerException
    public void createLike(int filmId, int userId) throws NullPointerException{
        Film film = filmStorage.findFilmById(filmId);
        //проверяем существует ли автор
        userStorage.findUserById(userId);
        Set<Integer> likeFilmUserId = new HashSet(film.getLike());
        if (!(likeFilmUserId.add(userId))) {
            log.error("автор с id {} ранее добавил лайк фильму с id {}", userId, filmId);
        }
        film.setLike(likeFilmUserId);
        filmStorage.update(film);
        log.info("автор с id {} добавил лайк фильму с id {}", userId, filmId);

    }

    //удаляем лайк фильма
    //Если фильм или автор не найден выбрасываем NullPointerException
    public void deleteLike(int filmId, int userId) throws NullPointerException{
        Film film = filmStorage.findFilmById(filmId);
        //проверяем существует ли автор
        userStorage.findUserById(userId);
        Set<Integer> likeFilmUserId = new HashSet(film.getLike());
        if (!(likeFilmUserId.remove(userId))) {
            log.error("автор с id {} ранее не добавлял лайк фильму с id {}", userId, filmId);
        }
        film.setLike(likeFilmUserId);
        filmStorage.update(film);
        log.info("автор с id {} удалил лайк фильму с id {}", userId, filmId);

    }

    //сортируем фильмы по числу лайков и возвращаем первые count фильмы
    public List<Film> sortFilmByLike(int count){
        return filmStorage.getFilms().stream()
                .sorted(new FilmByLikeComparator())
                .limit(count)
                .collect(Collectors.toList());
    }

    class FilmByLikeComparator implements Comparator<Film>{

        public int compare(Film a, Film b){
            return b.getLike().size() - (a.getLike().size());
        }
}
}
