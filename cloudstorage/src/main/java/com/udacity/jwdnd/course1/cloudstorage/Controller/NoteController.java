package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NoteController {
    @Autowired
    NoteService noteService;

    @PostMapping("/add/note")
    public String addNote(@ModelAttribute("note") Note note, Model model, Authentication authentication){
        String username = (String) authentication.getPrincipal();
        noteService.addNote(username, model, note);
        return "result";
    }

    @GetMapping("/delete/note")
        public String deleteNote(@RequestParam("noteId") Integer noteId, Model model){
        noteService.deleteNote(noteId);
        model.addAttribute("success",true);
            return "result";
        }
    }

