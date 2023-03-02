package org.hyl.cloudnote.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.hyl.cloudnote.dao.BookDao;
import org.hyl.cloudnote.dao.NoteDao;
import org.hyl.cloudnote.entity.Book;
import org.hyl.cloudnote.util.NoteResult;
import org.hyl.cloudnote.util.NoteUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {
	@Resource
	private BookDao bookDao;
	@Resource
	private NoteDao noteDao;
	@Transactional(readOnly=true)
	public NoteResult loadUserBooks(String userId) {
		NoteResult result = new NoteResult();
		List<Book> books = bookDao.findByUserId(userId);
		result.setStatus(0);
		result.setMsg("加载笔记本成功");
		result.setData(books);
		return result;
	}
	
	public NoteResult addBook(String bookName,String userId) {
		NoteResult result = new NoteResult();
		Book book = new Book();
		String bookId = NoteUtil.createId();
		book.setCn_notebook_name(bookName);
		book.setCn_user_id(userId);
		book.setCn_notebook_id(bookId);
		book.setCn_notebook_type_id("5");
		book.setCn_notebook_desc("");
//		Timestamp time = new Timestamp(System.currentTimeMillis());
//		book.setCn_notebook_createtime(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		String strDate = sdf.format(ts);
		book.setCn_notebook_createtime(Timestamp.valueOf(strDate));
		bookDao.save(book);
		result.setStatus(0);
		result.setMsg("新增笔记本成功");
		result.setData(book.getCn_notebook_id());
		return result;
	}

	public NoteResult deleteBook(String bookId) {
		int rows = bookDao.deleteBook(bookId);
		NoteResult result = new NoteResult();
		if(rows != 0){
			result.setStatus(0);
			result.setMsg("删除笔记本成功");
		}else{
			result.setStatus(1);
			result.setMsg("删除笔记本成功");
		}
		//同时删除该笔记本下的所有笔记
		noteDao.deleteNotes(bookId);
		return result;
	}

	public NoteResult renameBook(String bookName, String bookId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bookName", bookName);
		map.put("bookId", bookId);
		int rows = bookDao.renameBook(map);
		NoteResult result = new NoteResult();
		if(rows != 0){
			result.setStatus(0);
			result.setMsg("笔记本重命名成功");
		}else{
			result.setStatus(1);
			result.setMsg("笔记本重命名失败");
		}
		return result;
	}
}
