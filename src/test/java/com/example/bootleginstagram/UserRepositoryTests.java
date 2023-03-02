package com.example.bootleginstagram;

import com.example.bootleginstagram.user.User;
import com.example.bootleginstagram.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("jane@test.com");
        user.setPassword("qwerty");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        
        User savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);

        for(User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Integer testUserId = 1;
        Optional<User> optionalUser = repo.findById(testUserId);
        User user = optionalUser.get();
        user.setPassword("newPW");

        repo.save(user);

        User updatedUser = repo.findById(testUserId).get();
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("newPW");

    }

    @Test
    public void testGet() {
        Integer testUserId = 1;
        Optional<User> optionalUser = repo.findById(testUserId);

        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete() {
        Integer testUserId = 1;
        repo.deleteById(testUserId);

        Optional<User> optionalUser = repo.findById(testUserId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }
}
