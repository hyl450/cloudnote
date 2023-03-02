package org.hyl.cloudnote.controller.notebook;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.BookService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notebook")
public class DeleteBookController {
	@Resource(name="bookService")
	private BookService bookService;
	@RequestMapping("/delbook.do")
	@ResponseBody
	public NoteResult execute(String bookId){
		NoteResult result = bookService.deleteBook(bookId);
		return result;
	}
}
