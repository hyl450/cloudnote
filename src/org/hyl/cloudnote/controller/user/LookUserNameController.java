package org.hyl.cloudnote.controller.user;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.UserService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class LookUserNameController {
	@Resource
	private UserService userService;
	@RequestMapping("/lookName.do")
	@ResponseBody
	public NoteResult execute(String userId){
		NoteResult result = userService.lookUserName(userId);
		return result;
	}
}
