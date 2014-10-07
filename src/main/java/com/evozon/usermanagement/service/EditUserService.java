package com.evozon.usermanagement.service;

import com.evozon.usermanagement.model.User;

public interface EditUserService {

	public String editUserInfo(User user);
	public String changePassword(User user, String currentPass,String newPass, String confirmPass);
	public String resetPassword(String email);
}
