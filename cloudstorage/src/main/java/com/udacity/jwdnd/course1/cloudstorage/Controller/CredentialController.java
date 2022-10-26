package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CredentialController {
    @Autowired
    CredentialService credentialService;

    @PostMapping("/add/credential")
    public String addCredential(@ModelAttribute("credential") Credential credential, Authentication authentication, Model model){
        String username = (String) authentication.getPrincipal();
        credentialService.addCredential(username, model, credential);
        return "result";
    }

    @RequestMapping("/delete/credential")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId, Model model){
        credentialService.deleteCredential(credentialId);
        model.addAttribute("success",true);
        return "result";
    }
}
