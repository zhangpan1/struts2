package com.eversec.zhangpan.proxy;
import org.junit.Test;
public class ProxyTest {
	@Test
	public void testProxy(){
		PersonDao personDao = new PersonDaoImpl();
		Transaction transtion = new Transaction();
		PersonDaoProxy proxy= new PersonDaoProxy(personDao,transtion);
		proxy.savePerson();
	}
}
