package org.hyl.cloudnote.controller.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAddNoteController {
	private ApplicationContext ac;
	@Before//在使用test之前调用此方法
	public ApplicationContext getContext(){
		String[] confs = {"spring-bean.xml","spring-mybatis.xml","spring-mvc.xml"};
		ac = new ClassPathXmlApplicationContext(confs);
		return ac;
	}
	@Test
	public void test1(){
		ApplicationContext ac = getContext();
		System.out.println(ac.getBean("addNoteController"));
	}
}
