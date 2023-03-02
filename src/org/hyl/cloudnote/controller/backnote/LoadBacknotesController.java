package org.hyl.cloudnote.controller.backnote;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.BookService;
import org.hyl.cloudnote.service.NotesService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/backnote")
public class LoadBacknotesController {
	@Resource
	private NotesService notesService;
	@RequestMapping("/loadbacknotes.do")
	@ResponseBody
	public NoteResult execute(String userId){
		NoteResult result = notesService.loadBackNotes(userId);
		return result;
	}
}
