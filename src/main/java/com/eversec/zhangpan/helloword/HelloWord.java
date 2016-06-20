package com.eversec.zhangpan.helloword;

public class HelloWord {
	//在Spring容器中，默认调用了  默认构造器来 创建对象
	public HelloWord(){
		System.out.println("createObject");
	}
	public void hello(){
		System.out.println("hello world");
	}
}
