package com.in28minutes.login;

import org.springframework.stereotype.Service;

@Service
public class LoginService {
	public boolean isUserValid(String user,String password) {
		if(user.equals("admin")&& password.equals("@Abkky121"))
			return true;
		return false;
	}
}
