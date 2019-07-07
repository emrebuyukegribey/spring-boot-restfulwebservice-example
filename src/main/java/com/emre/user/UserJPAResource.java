package com.emre.user;

import com.emre.blog.Blog;
import com.emre.blog.BlogRepository;
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
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UserJPAResource {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlogRepository blogRepository;

    @GetMapping("/jpa/users")
    public List<User> retrieveAllUsers() {

        return userRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}")
    public Resource<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            throw new UserNotFoundException(ExceptionUtils.getExceptionMessage(ExceptionMessages.USER_NOT_FOUND_EXCEPTION) + " userId -> " + id);
        }

        //"all-users", SERVER_PATH + "/users"
        //retrieveAllUsers
        Resource<User> resource = new Resource<User>(user.get());

        ControllerLinkBuilder linkToAllUsers = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        ControllerLinkBuilder linkToUser = linkTo(methodOn(this.getClass()).retrieveUser(1));
        ControllerLinkBuilder linkToAddUser = linkTo(methodOn(this.getClass()).createUser(new User()));

        resource.add(linkToAllUsers.withRel("all-users"));
        resource.add(linkToUser.withRel("get-user"));
        resource.add(linkToAddUser.withRel("create-user"));

        //HATEOAS

        return resource;
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }


    @GetMapping("/jpa/users/{id}/blogs")
    public List<Blog> retrieveAllUsers(@PathVariable int id) {

        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()) {
            throw new UserNotFoundException(ExceptionUtils.getExceptionMessage(ExceptionMessages.USER_NOT_FOUND_EXCEPTION) + " userId -> "+id);
        }
        return user.get().getBlogs();
    }

    @PostMapping("/jpa/users/{id}/blogs")
    public ResponseEntity<Object> createBlog(@PathVariable int id, @RequestBody Blog blog) {

        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException(ExceptionUtils.getExceptionMessage(ExceptionMessages.USER_NOT_FOUND_EXCEPTION) + " userId -> "+id);
        }

        User user = userOptional.get();

        blog.setUser(user);

        blogRepository.save(blog);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(blog.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}
