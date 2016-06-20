package com.eversec.zhangpan.jdkproxy;
import java.lang.reflect.Proxy;

import org.junit.Test;
/**
 * 1.拦截器的invoke方法 ，是在什么时候执行的
 * 		当在客户端，代理对象调用方法的时候，进入到了拦截器的invoke方法
 * 2.代理对象的方法体的内容是什么
 * 		拦截器的invoke方法的内容就是代理对象的方法的内容
 * 3.拦截器中的invoke方法中的参数method是谁在什么时候传递过来的？
 * 		代理对象调用方法的时候，进入了拦截器中的invoke方法，所以invoke方法中的参数
 * 		method就是代理对象调用的方法 
 * @author zhangp
 *
 */
public class JDKProxyTest {
	@Test
	public void testJDKProxy(){
		/**
		 * 1、创建一个目标对象
		 * 2、创建一个事务
		 * 3、创建一个拦截器
		 * 4、动态超声一个代理对象
		 */
		Object target = new PersonDaoImpl();
		Transaction transaction = new Transaction();
		MyInterceptor interceptor = new MyInterceptor(target, transaction);
		/**
		 * 1.目标类的类加载器
		 * 2.目标类实现的所有的接口
		 * 3.拦截器
		 */
		PersonDao personDao =(PersonDao) Proxy.newProxyInstance(target.getClass().getClassLoader(), 
				target.getClass().getInterfaces(), interceptor);
		personDao.savePerson();
	}
}
