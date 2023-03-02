package org.hyl.cloudnote.dao;

import org.hyl.cloudnote.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
	public User findByName(String name);
	public void save(User user);
	public void alterPwd(User user);
	public User findById(String id);
}
