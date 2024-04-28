package com.demo.journalapp.controller;

import com.demo.journalapp.entity.User;
import com.demo.journalapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    private final UserService userService;

    public PublicController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("Creating user");
        User createdUser =  userService.saveNewUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
