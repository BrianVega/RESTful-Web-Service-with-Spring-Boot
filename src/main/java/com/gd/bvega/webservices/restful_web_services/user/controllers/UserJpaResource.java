package com.gd.bvega.webservices.restful_web_services.user.controllers;

import com.gd.bvega.webservices.restful_web_services.user.entities.Post;
import com.gd.bvega.webservices.restful_web_services.user.entities.User;
import com.gd.bvega.webservices.restful_web_services.user.UserNotFoundException;
import com.gd.bvega.webservices.restful_web_services.user.repositories.PostRepository;
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
    private final PostRepository postRepository;

    public UserJpaResource(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
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

    @GetMapping(path = "/jpa/users/{userId}/posts")
    public List<Post> getPostForUser(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty())
            throw new UserNotFoundException("User with id " + userId + " not found");

        return user.get().getPosts();
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

    @PostMapping(path = "/jpa/users/{userId}/posts")
    public ResponseEntity<Post> createPostForUser(@PathVariable Integer userId, @RequestBody Post post) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty())
            throw new UserNotFoundException("User with id " + userId + " not found");

        post.setUser(user.get());

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedPost);
    }

    @GetMapping(path = "/jpa/users/{userId}/posts/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Integer userId, @PathVariable Integer postId) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty())
            throw new UserNotFoundException("User with id " + userId + " not found");

        Optional<Post> post = postRepository.findById(postId);

        if(post.isEmpty())
            throw new UserNotFoundException("Post with id " + postId + " not found");

        return ResponseEntity.ok(post.get());
    }
}

