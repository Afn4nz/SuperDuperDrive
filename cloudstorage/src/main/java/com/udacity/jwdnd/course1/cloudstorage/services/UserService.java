package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Entity.User;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    HashService hashService;

    public int createUser(Model model, User user){
        if (!isUsernameAvailable(user.getUsername())){
            model.addAttribute("signupError", "Sorry! the username already exists.");
            return -1;
        }
        hashPassword(user);
        int rowsAdded = userMapper.insertUser(user);
        if (rowsAdded > 0) {
            model.addAttribute("signupSuccess", true);
            return rowsAdded;
        }
        model.addAttribute("signupError", "Sorry! there was an error signing you up. Please try again.");
        return -1;
    }

    private boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    private User hashPassword(User user){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        user.setSalt(encodedSalt);
        user.setPassword(hashedPassword);
        return user;
    }

    public Integer getUserId(String username) {
        return userMapper.getUser(username).getUserId();
    }
}
