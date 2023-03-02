package org.hyl.cloudnote.controller.notebook;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.BookService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notebook")
public class AddBookController {
	@Resource
	private BookService bookService;
	@RequestMapping("/add.do")
	@ResponseBody
	public NoteResult execute(String bookName,String userId){
		NoteResult result = bookService.addBook(bookName, userId);
		return result;
	}
}
