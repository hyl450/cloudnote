package org.hyl.cloudnote.service;

import org.hyl.cloudnote.util.NoteResult;

//public NoteResult xxx(请求参数);
public interface UserService {
	public NoteResult registUser(String name, String password, String desc) throws Exception;
	public NoteResult checkLogin(String name, String password) throws Exception;
	public NoteResult updatePwd(String userId, String oldPwd, String newPwd)throws Exception;
	public NoteResult lookUserName(String userId);
}
