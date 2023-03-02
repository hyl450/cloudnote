package org.hyl.cloudnote.controller.note;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.NotesService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/note")
public class MoveNoteController {
	@Resource
	private NotesService noteService;
	@RequestMapping("/move.do")
	@ResponseBody
	public NoteResult execute(String noteId,String bookId){
		NoteResult result = noteService.moveNote(noteId, bookId);
		return result;
	}
}
