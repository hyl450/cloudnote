package org.hyl.cloudnote.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerBean {
	private static Logger logger = Logger.getLogger(LoggerBean.class);
	@Before("within(org.hyl.cloudnote.controller..*)")
	public void logController(){
		//编写要追加的处理逻辑
		System.out.println("--进入logController处理--");
		logger.info("--进入logController处理--");
	}
}
