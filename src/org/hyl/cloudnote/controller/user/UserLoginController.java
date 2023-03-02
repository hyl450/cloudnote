package org.hyl.cloudnote.controller.user;

import javax.annotation.Resource;

import org.hyl.cloudnote.service.UserService;
import org.hyl.cloudnote.util.NoteResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserLoginController {
	@Resource
	private UserService userService;
	
	@RequestMapping("/login.do")
	@ResponseBody//返回JSON字符串
	public NoteResult execute(String name,String password) throws Exception{
		System.out.println("execute方法");
		NoteResult result = userService.checkLogin(name, password);
		System.out.println(result);
		return result;
	}
}
