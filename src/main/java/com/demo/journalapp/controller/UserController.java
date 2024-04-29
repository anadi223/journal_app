package com.demo.journalapp.controller;

import com.demo.journalapp.dtos.QuoteResponse;
import com.demo.journalapp.dtos.WeatherResponse;
import com.demo.journalapp.entity.User;
import com.demo.journalapp.service.QuoteService;
import com.demo.journalapp.service.UserService;
import com.demo.journalapp.service.WeatherService;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final WeatherService weatherService;
    private final QuoteService quoteService;

    public UserController(UserService userService, WeatherService weatherService, QuoteService quoteService) {
        this.userService = userService;
        this.weatherService = weatherService;
        this.quoteService = quoteService;
    }

    @GetMapping("all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable ObjectId id) {
        return userService.getUserById(id);
    }

    @PutMapping("/update-user")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        //We will get the user from the authorization that we are setting in postman,
        //and spring security holds the information in securitycontextholder and based on that we will go ahead and fetch the username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.updateUser(user,username);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userService.deleteByUserName(username);
    }

    @GetMapping("/greet")
    public String greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        QuoteResponse quoteResponse = quoteService.getRandomQuote();
        String quote = quoteResponse.getQuote();
        String greeting = "";
        if(weatherResponse!= null){
            greeting = ", Weather feels like " + weatherResponse.getMain().getFeelsLike()+ " degrees. Here is a beautiful quote for you " +quote;
            return "Hi " + authentication.getName()+greeting;
        }
        return "Hi " + authentication.getName();
    }
}
