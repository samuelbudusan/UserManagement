package com.evozon.usermanagement.service;

import com.evozon.usermanagement.dao.UserDAO;
import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.Permission;
import com.evozon.usermanagement.model.UserRole;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.utils.UserUtil;
import com.evozon.usermanagement.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NewItemServiceImpl implements NewItemService {
	
	@Autowired
	UserDAO uDAO;
	
	@Autowired
	Validator<User> validator;
	
	@Autowired
	UserService userService;

    @Autowired
    GroupService groupService;

	@Override
	public String addUser(User user) {
		String errors;
        user.setEnabled(1);
		String userName = user.getUserName();
		if(userService.loadUserByUsername(userName) == null) {
			errors = validator.validate(user);
			if( errors.equals("")) {
				user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
                uDAO.addUser(user);
                UserRole role = new UserRole(user,"ROLE_COMMON",null);
                uDAO.addRole(role);
                Set<Permission> permissions = UserUtil.createPermissionList(role);
                uDAO.addPermissions(permissions);
			}
		} else {
			errors = "The given username is already in use,";
		}
		
		return errors;
	}

    public String addGroup(Group group) {
        String errors="";

       if( groupService.loadGroupByName(group.getGroupName()) == null ) {
           uDAO.addGroup(group);
       } else {
           errors = "The given group name is already in use.";
       }
        return errors;
    }
}
