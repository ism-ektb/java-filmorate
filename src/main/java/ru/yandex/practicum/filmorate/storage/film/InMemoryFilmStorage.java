package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Deprecated
/**
 * Сервисный класс для хранения экземпляров класса Film в памяти компьютера
 * если экземпляр с необходимым значением поля id отсутствует, то
 * выбрасывается исключение NullPointerException
 * поддерживается интерфейс FilmStorage
 */
public class InMemoryFilmStorage implements FilmStorage {

    private Map<Integer, Film> films = new HashMap<>();

    @Override
    public Film create(Film film) {
        films.put(film.getId(), film);
        log.debug("Записан новый фильм {} с id {}", film.getName(), film.getId());
        return film;
    }

    @Override
    public Film findFilmById(int id) {
        if (films.containsKey(id)) {
            log.debug("Найден фильм с id {}", id);
            return films.get(id);
        } else {
            log.error("Фильм с id {} отсутствует", id);
            throw new NullPointerException();
        }
    }

    @Override
    public Film update(Film film) {
        if (films.containsKey(film.getId())) {
            log.debug("Фильм {} c id {} заменен", film.getName(), film.getId());
            films.replace(film.getId(), film);
            return film;
        } else {
            log.error("Фильм с id {} отсутствует", film.getId());
            throw new NullPointerException();
        }
    }

    @Override
    public void delete(int id) {
        if (films.remove(id) != null) {
            log.debug("Фильм c id {} удален", id);
        } else {
            log.error("Фильм с id {} отсутствует", id);
        }
    }

    @Override
    public List<Film> getFilms() {
        log.debug("Всего фильмов {}", films.size());
        return new ArrayList(films.values());
    }
}
