package org.hyl.cloudnote.service;

import org.hyl.cloudnote.util.NoteResult;

public interface ShareService {
	public NoteResult shareNote(String noteId);
	public NoteResult searchNote(String noteTitle);
	public NoteResult loadShareNote(String noteId);
}
