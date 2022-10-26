package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    FileService fileService;
    @Autowired
    NoteService noteService;
    @Autowired
    CredentialService credentialService;

    @GetMapping("/home")
    public String homeView(Model model, Authentication authentication){
        String username = (String) authentication.getPrincipal();
        model.addAttribute("files", fileService.getFiles(username));
        model.addAttribute("notes", noteService.getAllNotes(username));
        model.addAttribute("credentials", credentialService.getAllCredentials(username));
        model.addAttribute("credentialService", credentialService);
        return "home";
    }
}
