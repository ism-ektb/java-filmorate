package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;

@Slf4j
@Service
public class GenreService {

    @Autowired
    private GenreStorage genreStorage;

    /**
     * @return список всех экзепляров класса genre из храницища
     */
    public List<Genre> getGenres() {
        return genreStorage.getAll();
    }

    /**
     * @param id - жандра
     * @return возвращает экземпляр класса genre с id которого соответствует входному паратетру
     * @throws NullPointerException - Если жанр с переданным id не найден
     */
    public Genre getGenreById(Integer id) {
        return genreStorage.getGenreById(id);
    }

}
