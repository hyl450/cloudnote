package org.hyl.cloudnote.controller.activity;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.ActivityService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/activity")
public class LoadActivityController {
	@Resource
	private ActivityService activityService;
	@RequestMapping("/load.do")
	@ResponseBody
	public NoteResult execute(){
		NoteResult result = activityService.loadActivity();
		return result;
	}
	
}
