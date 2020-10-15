package com.revature.testing;

import com.revature.dao.implementations.UserDaoImpl;

public class Driver {

	public static void main(String[] args) {
		UserDaoImpl userDao = new UserDaoImpl();
		System.out.println(userDao.getById(1));
	}
}
