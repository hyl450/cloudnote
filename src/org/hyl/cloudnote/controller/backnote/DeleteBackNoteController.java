package org.hyl.cloudnote.controller.backnote;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.NotesService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/backnote")
public class DeleteBackNoteController {
	@Resource
	private NotesService notesService;
	@RequestMapping("/delbacknote.do")
	@ResponseBody
	public NoteResult execute(String noteId){
		NoteResult result = notesService.deleteBackNote(noteId);
		return result;
	}
}
