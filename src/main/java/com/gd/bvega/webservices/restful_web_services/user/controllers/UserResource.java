package com.gd.bvega.webservices.restful_web_services.user.controllers;

import com.gd.bvega.webservices.restful_web_services.user.User;
import com.gd.bvega.webservices.restful_web_services.user.UserDaoService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
    public EntityModel<User> getUsers(@PathVariable Integer userId) {
        User user = userDaoService.findOne(userId);

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUsers());
        entityModel.add(linkTo.withRel("users"));

        return entityModel;
    }



    @DeleteMapping(path = "users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userDaoService.deleteUserById(userId);
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userDaoService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(user);
    }
}
