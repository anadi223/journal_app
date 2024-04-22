package com.demo.journalapp.service;

import com.demo.journalapp.entity.User;
import com.demo.journalapp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService{
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User saveUser(User user) {

        userRepo.save(user);
        return user;
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

}
