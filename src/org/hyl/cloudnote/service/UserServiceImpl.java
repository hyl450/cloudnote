package org.hyl.cloudnote.service;

import javax.annotation.Resource;

import org.hyl.cloudnote.dao.UserDao;
import org.hyl.cloudnote.entity.User;
import org.hyl.cloudnote.util.NoteResult;
import org.hyl.cloudnote.util.NoteUtil;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")//扫描
@Transactional
public class UserServiceImpl implements UserService {
	@Resource//注入
	private UserDao userDao;
	@Transactional//checkLogin里面逻辑是一个整体，有错就把sql撤销，没错就执行。
	public NoteResult checkLogin(String name, String password) throws Exception {
		NoteResult result = new NoteResult();
		//检测用户名和密码
		User user = userDao.findByName(name);
		if(user==null){
			result.setStatus(1);
			result.setMsg("用户不存在");
			return result;
		}
		//将用户输入password加密
		String md5_pwd = NoteUtil.md5(password);
		//密文比对
		if(!user.getCn_user_password().equals(md5_pwd)){
			result.setStatus(2);
			result.setMsg("密码错误");
			return result;
		}
		result.setStatus(0);
		result.setMsg("登录成功");
		//把用户ID返回给ajax回调函数
		result.setData(user.getCn_user_id());
		return result;
	}
	
	public NoteResult registUser(String name, String password, String nick) throws Exception {
		NoteResult result = new NoteResult();
		//用户名唯一性检测
		User has_user = userDao.findByName(name);
		if(has_user!=null){
			result.setStatus(1);
			result.setMsg("用户名已存在");
			return result;
		}
		//执行添加
		User user = new User();
		
		String userId = NoteUtil.createId();
		user.setCn_user_id(userId);//设置用户ID
		
		user.setCn_user_name(name);//设置用户名
		String md5_pwd = NoteUtil.md5(password);
		user.setCn_user_password(md5_pwd);//设置加密
		user.setCn_user_nick(nick);//设置昵称
		userDao.save(user);//添加用户
		result.setStatus(0);
//		String s = null;
//		s.toString();
		result.setMsg("注册用户成功");		
		return result;
	}

	public NoteResult updatePwd(String userId,String lastPwd, String newPwd) throws Exception {
		NoteResult result = new NoteResult();
		User has_user = userDao.findById(userId);
		String md5OldPwd = NoteUtil.md5(lastPwd);

		if(!has_user.getCn_user_password().equals(md5OldPwd)){
			result.setStatus(1);
			result.setMsg("旧密码不正确");
			return result;
		}
		String md5Pwd = NoteUtil.md5(newPwd);
		User user = new User();
		user.setCn_user_id(userId);
		user.setCn_user_password(md5Pwd);
		userDao.alterPwd(user);
		result.setStatus(0);
		result.setMsg("更新密码成功");
		
		return result;
	}

	public NoteResult lookUserName(String userId) {
		NoteResult result = new NoteResult();
		User user = userDao.findById(userId);
		result.setStatus(0);
		result.setMsg("查询用户名成功");
		result.setData(user);
		return result;
	}

}
