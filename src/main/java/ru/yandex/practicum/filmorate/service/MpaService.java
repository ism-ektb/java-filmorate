package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.List;

@Slf4j
@Service
public class MpaService {

    @Autowired
    private MpaStorage mpaStorage;

    /**
     * @return список всех экзепляров класса mpa из храницища
     */
    public List<Mpa> getMpa() {
        return mpaStorage.getAll();
    }

    /**
     * @param id - зуйтинга
     * @return возвращает экземпляр класса mpa с id которого соответствует входному параметру
     * @throws NullPointerException - Если рейтинг с переданным id не найден
     */
    public Mpa getMpaById(Integer id) {
        return mpaStorage.getMpaById(id);
    }

}