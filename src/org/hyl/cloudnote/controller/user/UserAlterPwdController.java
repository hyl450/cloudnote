package org.hyl.cloudnote.controller.user;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.UserService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserAlterPwdController {
	@Resource
	private UserService userService;
	@RequestMapping("/alterPwd.do")
	@ResponseBody
	public NoteResult execute(String userId,String lastPwd,String newPwd) throws Exception{
		NoteResult result = userService.updatePwd(userId,lastPwd,newPwd);
		return result;
	}
}
