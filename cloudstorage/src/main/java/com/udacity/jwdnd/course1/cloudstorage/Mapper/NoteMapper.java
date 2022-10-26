package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) " +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Update("UPDATE NOTES SET noteTitle = #{noteTitle}, noteDescription = #{noteDescription} WHERE noteId = #{noteId}")
    int updateNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
     List<Note> getAllNotes(Integer userId);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void deleteNote(Integer noteId);

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(Integer noteId);
    @Select("SELECT * FROM NOTES WHERE noteTitle = #{noteTitle}")
    Note getNoteByTitle(String title);
}
