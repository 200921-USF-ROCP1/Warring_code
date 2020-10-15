package com.revature.services;

import com.revature.dao.implementations.AccountDaoImpl;
import com.revature.dao.implementations.UsersAccountsDaoImpl;

public class MiscService {
	//This class holds miscellaneous helper functions like isInt(String s)
	
	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isOwner(int userId, int accountId) {
		UsersAccountsDaoImpl usersAccountsDao = new UsersAccountsDaoImpl();
		return usersAccountsDao.getByIds(userId, accountId);
	}
	
	public static boolean doesAccountExist(int accountId) {
		AccountDaoImpl accountDao = new AccountDaoImpl();
		if (accountDao.getById(accountId) != null) return true;
		else return false;
	}
}