package org.hyl.cloudnote.controller.backnote;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.NotesService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/backnote")
public class ReplayNoteController {
	@Resource
	private NotesService notesService;
	@RequestMapping("/replay.do")
	@ResponseBody
	public NoteResult execute(String newBookId,String noteId){
		NoteResult result = notesService.replayNote(newBookId, noteId);
		return result;
	}
}
