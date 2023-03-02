package org.hyl.cloudnote.aop;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Component("handlerExcepiton")//扫描等价于<bean>
@Aspect//定义为切面，等价于<aop:aspect>
public class HandlerException {
	private static Logger logger = Logger.getLogger(HandlerException.class);
	//定义异常通知，等价于<aop:after-throwing)
	@AfterThrowing(throwing="ex",pointcut="within(org.hyl.cloudnote.controller..*)")
	public void execute(Exception ex){
		//将异常信息记录文件
		try {
			FileWriter fw = new FileWriter("cloud_note.log",true);
			PrintWriter out = new PrintWriter(fw);
			//打印out一个头部
			
			String header1 = "*********************************************";
			out.println(header1);
			out.println("时间："+new Date());
			out.println("类型："+ex.toString());
			String header2 ="*********************************************";
			out.println(header2);
			ex.printStackTrace(out);//将异常栈信息写入cloud_note.log文件
			out.close();
			fw.close();
		} catch (Exception e) {
			System.out.println("记录异常信息失败");
		}
	}
}
