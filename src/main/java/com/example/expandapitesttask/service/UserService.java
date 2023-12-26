package com.example.expandapitesttask.service;

import com.example.expandapitesttask.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    User create(User user);
    User readById(long id);
    User update(User user);
    void delete(long id);
    List<User> getAll();
    User readByEmail(String email);
    UserDetailsService userDetailsService();

}
