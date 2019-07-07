package com.emre.post;

import com.emre.enums.ExceptionMessages;
import com.emre.exception.ExceptionUtils;
import com.emre.user.User;
import com.emre.user.UserDaoService;
import com.emre.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class PostResource {

    @Autowired
    private PostDaoService service;

    @Autowired
    private UserDaoService userDaoService;

    @GetMapping("/users/{id}/posts")
    public List<Post> retrieveAllPosts(@PathVariable int id) {
        User user = userDaoService.findOne(id);
        List<Post> userPosts = service.findAll(id);

        if(user == null) {
            throw new UserNotFoundException(ExceptionUtils.getExceptionMessage(ExceptionMessages.USER_NOT_FOUND_EXCEPTION) + " userId -> " + id);
        }

        if(userPosts == null) {
            throw new PostNotSavedException(ExceptionUtils.getExceptionMessage(ExceptionMessages.POST_NOT_FOUND_EXCEPTION) + " post -> " + userPosts.toString());
        }
        return service.findAll(id);
    }

    @GetMapping("/users/{userId}/posts/{id}")
    public Post retrievePost(@PathVariable int userId, @PathVariable int id) {
        User user = userDaoService.findOne(userId);
        Post post =  service.findOne(id);

        if(user == null) {
            throw new UserNotFoundException(ExceptionUtils.getExceptionMessage(ExceptionMessages.USER_NOT_FOUND_EXCEPTION) + " userId -> " + id);
        }

        if(post == null) {
            throw  new PostNotFountException(ExceptionUtils.getExceptionMessage(ExceptionMessages.POST_NOT_FOUND_EXCEPTION) + " post id -> " +id);
        }
        return post;
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Object> addPost(@PathVariable int id, @RequestBody Post post) {
        Post savedPost = service.savePost(id, post);
        User user = userDaoService.findOne(id);

        if(user == null) {
            throw new UserNotFoundException(ExceptionUtils.getExceptionMessage(ExceptionMessages.USER_NOT_FOUND_EXCEPTION) + " userId -> " + id);
        }

        if(savedPost == null) {
            throw new PostNotSavedException(ExceptionUtils.getExceptionMessage(ExceptionMessages.POST_NOT_FOUND_EXCEPTION) + " post -> " +post.toString());
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
