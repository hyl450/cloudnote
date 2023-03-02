package org.hyl.cloudnote.controller.sharenote;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.ShareService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/share")
public class SearchNoteController {
	@Resource
	private ShareService shareService;
	@RequestMapping("/search.do")
	@ResponseBody
	public NoteResult execute(String noteTitle){
		NoteResult result = shareService.searchNote(noteTitle);
		return result;
	}
}
