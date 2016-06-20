package com.eversec.zhangpan.jdkproxy;

public class PersonDaoImpl implements PersonDao {
	//一定要想为什么不用静态了
	public void savePerson(){
		System.out.println("save person");
	}
}
