package com.evozon.usermanagement.service;

import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.User;

public interface NewItemService {
	
	public String addUser(User user);
    public String addGroup(Group group);
}
