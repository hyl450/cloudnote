package org.hyl.cloudnote.dao.test;

import java.util.List;

import junit.framework.Assert;

import org.hyl.cloudnote.dao.BookDao;
import org.hyl.cloudnote.dao.UserDao;
import org.hyl.cloudnote.entity.Book;
import org.hyl.cloudnote.entity.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCase {
	@Test
	public void test1(){
		String conf = "Spring-mybatis.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		UserDao dao = ac.getBean("userDao",UserDao.class);
		User user = dao.findByName("demo");
		System.out.println(user);
		//采用断言，比对实际结果和预期结果
		Assert.assertNotNull(user);
		
	}
	
	@Test
	public void test2(){
		String conf = "Spring-mybatis.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		UserDao dao = ac.getBean("userDao",UserDao.class);
		User user = dao.findByName("demo1");
		//采用断言，比对实际结果和预期结果
		Assert.assertNotNull(user);
		
	}
	@Test
	public void test3(){
		String conf = "Spring-mybatis.xml";
		ApplicationContext ac = new ClassPathXmlApplicationContext(conf);
		BookDao dao = ac.getBean("bookDao",BookDao.class);
		List<Book> list = dao.findByUserId("demo");
		//采用断言，比对实际结果和预期结果
		Assert.assertNotNull(list);
		
	}
	@Test
	public void test4(){
		int a=1;
		System.out.println(1==a);
	}
}
