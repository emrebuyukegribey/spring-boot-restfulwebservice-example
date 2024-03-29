package com.emre.user;

import com.emre.enums.ExceptionMessages;
import com.emre.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserResource {

    @Autowired
    private UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);
        if(user == null) {
            throw new UserNotFoundException(ExceptionUtils.getExceptionMessage(ExceptionMessages.USER_NOT_FOUND_EXCEPTION) + " userId -> " + id);
        }

        //"all-users", SERVER_PATH + "/users"
        //retrieveAllUsers
        Resource<User> resource = new Resource<User>(user);

        ControllerLinkBuilder linkToAllUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        ControllerLinkBuilder linkToUser = linkTo(methodOn(this.getClass()).retrieveUser(1));
        ControllerLinkBuilder linkToAddUser = linkTo(methodOn(this.getClass()).createUser(new User()));

        resource.add(linkToAllUsers.withRel("all-users"));
        resource.add(linkToUser.withRel("get-user"));
        resource.add(linkToAddUser.withRel("create-user"));

        //HATEOAS

        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = service.deleteById(id);
        if(user == null) {
            throw new UserNotFoundException(ExceptionUtils.getExceptionMessage(ExceptionMessages.USER_NOT_FOUND_EXCEPTION) + " userId -> " + id);
        }
    }
}
