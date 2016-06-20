package com.eversec.zhangpan.helloword;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;
public class Test1 {
	
	@Test
	public void testHelloWord(){
		//启动spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		HelloWord helloword = (HelloWord)context.getBean("helloWorld");
	}
}
