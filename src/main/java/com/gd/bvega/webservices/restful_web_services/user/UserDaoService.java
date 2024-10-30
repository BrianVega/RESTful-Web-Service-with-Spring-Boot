package com.gd.bvega.webservices.restful_web_services.user;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static int usersCount = 0;

    static {
        users.add(new User(++usersCount, "Brian", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount, "Oscar", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount, "Ronaldo", LocalDate.now().minusYears(20)));
        users.add(new User(++usersCount, "Osvaldo", LocalDate.now().minusYears(15)));
        users.add(new User(++usersCount, "Julio", LocalDate.now().minusYears(27)));
    }


    public List<User> findAll() {
        return users;
    }

    public User findOne(int id) {
        Predicate<? super User> predicate = user -> user.getId().equals(id);
        return users.stream().filter(predicate).findFirst().orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User save(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    public void deleteUserById(Integer userId) {
        users.removeIf(user -> user.getId().equals(userId));
    }
}
