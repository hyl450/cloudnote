package org.hyl.cloudnote.service.test;

import junit.framework.Assert;

import org.hyl.cloudnote.service.UserService;
import org.hyl.cloudnote.util.NoteResult;
import org.junit.Test;

public class TestRegistUser extends ServiceTest{
	
	@Test
	public void test1() throws Exception{	
		UserService service = super.getContext().getBean("userService",UserService.class);
		NoteResult result = service.registUser("demo", "1234", "demo");
		Assert.assertEquals(1, result.getStatus());
	}
	@Test
	public void test2() throws Exception{	
		UserService service = super.getContext().getBean("userService",UserService.class);
		NoteResult result = service.registUser("hyl", "123123", "hyl");
		Assert.assertEquals(0, result.getStatus());
	}
	//select * from cn_note where cn_note_id='59b354f8-47ae-437d-a432-7d28736bd894';
}
