package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserStorage userStorage;
    private int nextId = 1;

    // Создать автора и присвоить id
    public User create(User user) {
        user.setId(nextId++);
    //    user.setFriends(Collections.EMPTY_LIST);
        return userStorage.create(user);
    }

    public List<User> getUsers() {
        return userStorage.getAllUser();
    }

    // Обновить данные об авторе
    public User update(User user) throws NullPointerException{
    //    if (user.getFriends() == null) user.setFriends(Collections.EMPTY_LIST);
        return userStorage.update(user);
    }

    // удалить автора
    public boolean delete(int userId) throws NullPointerException{
        return userStorage.delete(userId);
    }

    // Находим автора по Id
    // NullPointerException если автор не найден
    public User findUserById(int id) throws NullPointerException{
        return userStorage.findUserById(id);
    }

    // Добавить друга.
    // NullPointerException если автор не найден.
    public void addFriend(int userId, int friendId) throws NullPointerException{
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);

  /*      if (user == null || friend == null) {
            throw new NullPointerException();
        }*/
    //    boolean result = (user.getFriends().add(friendId)
    //            & friend.getFriends().add(userId));
    //    if (result) {
        //Взаимно добавляем пользователя в друзья
    Set<Integer> userList = new HashSet(user.getFriends());
       userList.add(friendId);
        user.setFriends(userList);
       userList = new HashSet(friend.getFriends());
           userList.add(userId);
        friend.setFriends(userList);


            userStorage.update(user);
            userStorage.update(friend);
            log.info("авторы в id {} и {} подружились", userId, friendId);
     //   } else log.error("авторы в id {} и {} подружились раньше", userId, friendId);


    }

    //возвращаем список друзей пользователя, если пользователя нет возвращаем NullPointerException
    public List<User> getFriends(int userId) throws NullPointerException{
        User user = userStorage.findUserById(userId);

     //   if (user == null) return null;
      //  return new ArrayList(user.getFriends());
return user.getFriends().stream().map(e -> userStorage.findUserById(e)).collect(Collectors.toList());
    }

    // Находим общих друзей. Если пользователь отсутствует возвращаем NullPointerException
    public List<User> getCommonFriends(int userId, int friendId) throws NullPointerException {
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);

      //if (user == null || friend == null) return null;

        return friend.getFriends().stream()
                .filter(user.getFriends()::contains)
                .map(e -> userStorage.findUserById(e))
                .collect(Collectors.toList());
    }

    // Удаление из друзей.
    // Если пользователь отсутствует выбрасываем NullPointerException
    public void delete(int userId, int friendId) throws NullPointerException {
        User user = userStorage.findUserById(userId);
        User friend = userStorage.findUserById(friendId);

        Set<Integer> userList = new HashSet(user.getFriends());
        userList.remove(friendId);
        user.setFriends(userList);
        userList = new HashSet(friend.getFriends());
        userList.remove(userId);
        friend.setFriends(userList);

            userStorage.update(user);
            userStorage.update(friend);
            log.debug("авторы в id {} и {} теперь не друзья", userId, friendId);

     //   } else log.error("авторы в id {} и {} не были друзьями", userId, friendId);



    }


}
