package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    NoteMapper noteMapper;
    @Autowired
    UserService userService;

    public void addNote(String username, Model model, Note note){
        if (note.getNoteDescription().length() > 1000) {
            model.addAttribute("resultError", "Sorry! the note description exceed the limit.");
            return;
        }

        Integer userId = userService.getUserId(username);
        note.setUserId(userId);

        // In case note was already added then I will update it
        if(isNoteAdded(note.getNoteId())) {
            updateNote(note);
            model.addAttribute("success", true);
            return;
        }

        // To avoid duplication
        if(isNoteDuplicated(note.getNoteTitle())){
            model.addAttribute("resultError", "Sorry! the note is duplicated");
            return;
        }

        int rowAdded= noteMapper.insertNote(note);
        if(rowAdded > 0 ){
            model.addAttribute("success", true);
            return;
        }
        model.addAttribute("resultError", "Sorry! something went wrong.");
    }

    public List<Note> getAllNotes(String username){
        Integer userId = userService.getUserId(username);
        return noteMapper.getAllNotes(userId);
    }

    public void deleteNote(Integer noteId){
        noteMapper.deleteNote(noteId);
    }

    private boolean isNoteAdded(Integer noteId){
        return noteMapper.getNote(noteId) != null;
    }

    public void updateNote(Note note){
        noteMapper.updateNote(note);
    }

    private boolean isNoteDuplicated(String title){
        return noteMapper.getNoteByTitle(title) != null;
    }
}
