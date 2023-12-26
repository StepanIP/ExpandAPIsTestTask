package com.example.expandapitesttask.db;

import com.example.expandapitesttask.models.User;
import com.example.expandapitesttask.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataBaseConnectionTests {

    @Autowired
    UserRepository userRepository;


    @Test
    @Transactional
    void testCreateUser(){
        User user = new User();
        user.setEmail("nyGmail@gmail.com");
        user.setPassword("somePASS12&*");
        int beforeSize = userRepository.findAll().size();

        userRepository.save(user);
        User actual = userRepository.findByEmail(user.getEmail());

        assertEquals(user.getPassword(), actual.getPassword());
        assertEquals(user.getEmail(), actual.getEmail());
        assertNotEquals(beforeSize, userRepository.findAll().size());
    }
}
