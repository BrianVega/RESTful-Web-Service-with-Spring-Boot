package com.gd.bvega.webservices.restful_web_services.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {

    private UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping(path = "/users")
    public List<User> getUsers() {
        return userDaoService.findAll();
    }

    @GetMapping(path = "/users/{userId}")
    public User getUsers(@PathVariable Integer userId) {
        return userDaoService.findOne(userId);
    }

    @DeleteMapping(path = "users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userDaoService.deleteUserById(userId);
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }
}
