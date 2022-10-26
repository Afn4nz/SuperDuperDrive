package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginView(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
        return "login";
    }

    @GetMapping("/signup")
    public String signupView(@ModelAttribute("user") User user, Model model){
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user, Model model){
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
        if(userService.createUser(model, user) > 0)
            return "login";

        return "signup";
    }
}
