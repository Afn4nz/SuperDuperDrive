package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    @Autowired
    CredentialMapper credentialMapper;
    @Autowired
    UserService userService;
    @Autowired
    EncryptionService encryptionService;

    public void addCredential(String username, Model model, Credential credential){


        Integer userId = userService.getUserId(username);
        credential.setUserId(userId);
        credential.setKey(getEncodedKey());

        // Encrypt password before adding it to DB
        encryptPassword(credential);

        // In case credential was already added then I will update it
        if(isCredentialAdded(credential.getCredentialId())) {
            updateCredential(credential);
            model.addAttribute("success", true);
            return;
        }

        // To avoid duplication
        if(isCredentialDuplicated(credential.getUsername())){
            model.addAttribute("resultError", "Sorry! the username is duplicated");
            return;
        }

        int rowAdded= credentialMapper.insertCredential(credential);
        if(rowAdded > 0 ){
            model.addAttribute("success", true);
            return;
        }
        model.addAttribute("resultError", "Sorry! something went wrong.");
    }

    private boolean isCredentialAdded(Integer credentialId){
        return credentialMapper.getCredential(credentialId) != null;
    }

    public void updateCredential(Credential credential){
        credentialMapper.updateCredential(credential);
    }

    public List<Credential> getAllCredentials(String username){
        Integer userId = userService.getUserId(username);
        return credentialMapper.getAllCredentials(userId);
    }

    public void deleteCredential(Integer credentialId ){
        credentialMapper.deleteCredential(credentialId);
    }

    private String getEncodedKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }

    public String decryptPassword(Credential credential){
        String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
        return decryptedPassword;
    }

    private Credential encryptPassword(Credential credential){
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        return credential;
    }

    private boolean isCredentialDuplicated(String username){
        return credentialMapper.getCredentialByUsername(username) != null;
    }
}
