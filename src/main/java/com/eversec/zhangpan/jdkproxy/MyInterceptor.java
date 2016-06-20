package com.eversec.zhangpan.jdkproxy;
//动态代理，要实现拦截器接口
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/**
 * 拦截器
 *    作用一：目标类导入进来
 *    作用二：事务导入进来
 *    作用三：invoke完成
 *    		1、开启事务
 *          2、调用目标对象的方法
 *          3、事务的提交
 * @author zhangp
 *
 */
public class MyInterceptor implements  InvocationHandler{
	private Object target;//目标类
	private Transaction transaction;
	public MyInterceptor(Object target,Transaction transaction){
		super();
		this.target = target;
		this.transaction = transaction;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		String methodName = method.getName();
		if ("savePerson".equals(methodName) || "updatePerson".equals(methodName)||
				"deletePerson".equals(methodName)){//开启事务
			method.invoke(target);//调用目标方法
			this.transaction.commit();//事务的提交
		}else {
			method.invoke(target);
		}
		return null;
	}

}
