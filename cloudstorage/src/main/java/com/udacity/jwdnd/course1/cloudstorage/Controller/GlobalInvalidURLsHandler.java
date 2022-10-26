package com.udacity.jwdnd.course1.cloudstorage.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalInvalidURLsHandler implements ErrorController {

    @GetMapping("/error")
    public String showError(Model model){
        model.addAttribute("home",true);
        return "invalidURLs";
    }
    @Override
    public String getErrorPath() {
        return null;
    }
}
