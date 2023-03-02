package org.hyl.cloudnote.service;

import org.hyl.cloudnote.util.NoteResult;

public interface NotesService {
	public NoteResult loadBookNotes(String bookId);
	public NoteResult loadNote(String noteId);
	public NoteResult addNote(String userId, String bookId, String noteName);
	public NoteResult saveNote(String noteId, String noteName, String noteDesc);
	public NoteResult recycleNote(String noteId);
	public NoteResult moveNote(String noteId, String bookId);
	public NoteResult loadBackNotes(String userId);
	public NoteResult deleteBackNote(String noteId);
	public NoteResult replayNote(String newBookId, String noteId);
}
