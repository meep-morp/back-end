package com.postit.userdata.controllers;

import com.postit.userdata.models.Posts;
import com.postit.userdata.models.Subreddit;
import com.postit.userdata.models.User;
import com.postit.userdata.services.SubredditService;
import com.postit.userdata.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubredditService subredditService;

    @GetMapping(value = "/users", produces = "application/json")
    ResponseEntity<?> getAll() {
        List<User> userList = userService.findAll();

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping(value = "/myinfo", produces = "application/json")
    public ResponseEntity<?> getMyInfo() {
        User user = userService.getCurrentUserInfo();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Transactional
    @PutMapping(value = "/user/{id}", consumes = "application/json")
    public ResponseEntity<?> updateUserFull(@Valid @RequestBody User newUser, @PathVariable long id) {
        newUser.setUserid(id);
        userService.save(newUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/subs/save", consumes = "application/json")
    public ResponseEntity<?> saveSub(@Valid @RequestBody Subreddit subreddit) {
        userService.SaveSubreddit(subreddit);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
