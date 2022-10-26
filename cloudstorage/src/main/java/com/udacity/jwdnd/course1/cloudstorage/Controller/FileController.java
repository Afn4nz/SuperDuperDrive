package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Entity.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class FileController {
    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload
                            , Model model) throws IOException {

        //Check if the file is bigger than 5MB
        if((fileUpload.getSize() > 5242880)) {
            throw new MaxUploadSizeExceededException(fileUpload.getSize());
        }

        String username = (String) authentication.getPrincipal();
        fileService.insertFile(fileUpload, username, model);
        return "result";
    }

    @GetMapping("/delete/file")
    public String deleteFile(@RequestParam("fileId") Integer fileId, @ModelAttribute("file") File file, Model model) {
        fileService.deleteFile(fileId);
        model.addAttribute("success", true);
        return "result";
    }

    @GetMapping( value="/view/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> viewFile(@RequestParam("fileId") Integer fileId) {
        File file = fileService.getFileById(fileId);
        String fileName = file.getFileName();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+fileName+"\"")
                .body(file.getFileData());
    }
}
