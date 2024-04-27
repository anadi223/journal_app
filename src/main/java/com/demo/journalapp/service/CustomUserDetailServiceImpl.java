package com.demo.journalapp.service;

import com.demo.journalapp.entity.User;
import com.demo.journalapp.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;
    public CustomUserDetailServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if(user!=null){
            return org.springframework.security.core.userdetails.User.builder().
                    username(user.getUserName()).
                    password(user.getPassword()).
                    roles(user.getRoles().toArray(new String[0])).
                    build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
