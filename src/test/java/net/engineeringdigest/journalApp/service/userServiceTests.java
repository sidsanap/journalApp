package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class userServiceTests {



    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @ParameterizedTest
    @CsvSource({
            "ram",
            "pooja.sanap@gmail.com",
            "siddheshwar.sanap@gmail.com",
            "siddheshwarrr.sanap@gmail.com"
    })
    public void testFindByUsername(String userName){
        assertNotNull(userRepository.findByUserName(userName));
    }


    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testSaveNewUser(User user) {
        // assertTrue(userService.saveNewUser(user));
    }


    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,9"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a + b);
    }



}
