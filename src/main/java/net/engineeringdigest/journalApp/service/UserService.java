package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(User user){
        try {
            userRepository.save(user);
        }catch(Exception e){
            log.error("Exception ",e);
        }
    }


    public void saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        }catch(Exception e){
            log.error("Exception logger ",e);
        }
    }

    public void  saveAdminUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("ADMIN","USER"));
            userRepository.save(user);
        }catch(Exception e){
            log.error("Exception ",e);
        }
    }

    public Optional<User> findById(ObjectId myId){
        return userRepository.findById(myId);

    }

    public void deleteById(ObjectId myId){
        userRepository.deleteById(myId);

    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }
}
