package com.demo.journalapp.service;

import com.demo.journalapp.entity.User;
import com.demo.journalapp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    private final UserRepo userRepo;
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User saveNewUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepo.save(user);
        return user;
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    public User getUserById(ObjectId id) {
        return userRepo.findById(id).orElse(null);
    }

    public boolean deleteUserEntryById(ObjectId id) {
        userRepo.deleteById(id);
        return true;
    }

    public User getUserByUserName(String username) {
        return userRepo.findByUserName(username);
    }

    public ResponseEntity<HttpStatus> updateUser(User user, String authenticatedUser) {
        User userInDB = userRepo.findByUserName(authenticatedUser);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepo.save(userInDB);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ResponseEntity<HttpStatus> deleteByUserName(String username) {
        userRepo.deleteByUserName(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
