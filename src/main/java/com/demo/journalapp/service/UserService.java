package com.demo.journalapp.service;

import com.demo.journalapp.entity.User;
import com.demo.journalapp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService{
    private final UserRepo userRepo;
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        userRepo.save(user);
        return user;
    }

    public void saveNewUser(User user) {

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

}
