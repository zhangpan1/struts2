package com.eversec.zhangpan.jdkproxy;
/**
 * 给事务、日志等做了一个抽象，而这个抽象就是Interceptor
 * 
 * @author zhangp
 *
 */
public interface Interceptor {
	public void interceptor();
}
