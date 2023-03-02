package org.hyl.cloudnote.service;

import org.hyl.cloudnote.util.NoteResult;

public interface BookService {
	public NoteResult loadUserBooks(String userId);
	public NoteResult addBook(String bookName, String userId);
	public NoteResult deleteBook(String bookId);
	public NoteResult renameBook(String bookName, String bookId);
}
