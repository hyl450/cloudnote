package org.hyl.cloudnote.service.test;

import junit.framework.Assert;

import org.hyl.cloudnote.service.UserService;
import org.hyl.cloudnote.util.NoteResult;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCheckLogin {
	@Test
	public void test1() throws Exception{
		String[] confs = {"spring-bean.xml","spring-mybatis.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(confs);
		UserService service = ac.getBean("userService",UserService.class);
		System.out.println(service.getClass().getName());
		NoteResult result = service.checkLogin("scott", "1234");
		//断言，返回的status应该为1
		//Assert.assertEquals(1, result.getStatus());
	}
	@Test
	public void test2() throws Exception{
		String[] confs = {"spring-bean.xml","spring-mybatis.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(confs);
		UserService service = ac.getBean("userService",UserService.class);
		NoteResult result = service.checkLogin("demo", "1234");
		//断言，返回的status应该为2
		Assert.assertEquals(2, result.getStatus());
	}
	@Test
	public void test3() throws Exception{
		String[] confs = {"spring-bean.xml","spring-mybatis.xml"};
		ApplicationContext ac = new ClassPathXmlApplicationContext(confs);
		UserService service = ac.getBean("userService",UserService.class);
		NoteResult result = service.checkLogin("demo", "c8837b23ff8aaa8a2dde915473ce0991");
		//断言，返回的status应该为0
		Assert.assertEquals(0, result.getStatus());
	}
}
