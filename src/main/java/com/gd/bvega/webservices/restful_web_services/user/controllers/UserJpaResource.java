package com.gd.bvega.webservices.restful_web_services.user.controllers;

import com.gd.bvega.webservices.restful_web_services.user.User;
import com.gd.bvega.webservices.restful_web_services.user.UserDaoService;
import com.gd.bvega.webservices.restful_web_services.user.UserNotFoundException;
import com.gd.bvega.webservices.restful_web_services.user.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJpaResource {

    private final UserRepository userRepository;

    public UserJpaResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/jpa/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }




    @GetMapping(path = "/jpa/users/{userId}")
    public EntityModel<User> getUsers(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty())
            throw new UserNotFoundException("User with id " + userId + " not found");

        EntityModel<User> entityModel = EntityModel.of(user.get());

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsers());
        entityModel.add(linkTo.withRel("users"));

        return entityModel;
    }



    @DeleteMapping(path = "/jpa/users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userRepository.deleteById(userId);
    }

    @PostMapping(path = "/jpa/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userRepository.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }
}
