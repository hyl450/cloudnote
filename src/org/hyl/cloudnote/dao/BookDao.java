package org.hyl.cloudnote.dao;

import java.util.List;
import java.util.Map;

import org.hyl.cloudnote.entity.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDao {
	public List<Book> findByUserId(String userId);
	public void save(Book book);
	public int deleteBook(String bookId);
	public int renameBook(Map<String, Object> map);
}
