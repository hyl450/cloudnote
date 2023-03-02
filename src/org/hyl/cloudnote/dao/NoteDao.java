package org.hyl.cloudnote.dao;

import java.util.List;
import java.util.Map;
import org.hyl.cloudnote.entity.Note;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDao {
	public List<Note> findByBookId(String bookId);
	public Note findById(String noteId);
	public void save(Note note);
	public int saveNote(Note note);
	public int updateStatus(Map<String, Object> map);
	public int updateBookId(Map<String, Object> map);
	public void deleteNotes(String bookId);
	public List<Note> findByUserId(String userId);
	public int deleteBackNote(String noteId);
	public int replayNote(Map<String, Object> map);
}
