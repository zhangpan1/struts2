package com.eversec.zhangpan.proxy;

public class PersonDaoProxy implements PersonDao {
	private PersonDao personDao;
	private Transaction transaction;
	public PersonDaoProxy(PersonDao personDao,Transaction transaction){
		this.personDao = personDao;
		this.transaction = transaction;
	}
	@Override
	public void savePerson() {
		/**
		 * 1、开启事务
		 * 2、执行目标方法
		 * 3、事务提交
		 */
		this.transaction.beginTransaction();
		this.personDao.savePerson();
		this.transaction.commit();
	}

}
