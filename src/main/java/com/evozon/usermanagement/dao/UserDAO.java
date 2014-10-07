package com.evozon.usermanagement.dao;

import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.Permission;
import com.evozon.usermanagement.model.UserRole;
import com.evozon.usermanagement.model.User;

import java.util.List;
import java.util.Set;

public interface UserDAO {

	public List<User> getAllUsers();
	public List<Group> getAllGroups();
	public void updateUsers(List<User> usersList,int index);
	public void addUser(User user);
    public void addRole(UserRole role);
    public void addPermissions(Set<Permission> permissions);
    public List<User> getAllSimpleUsers();
    public User findByUserName(String username);
    public Group fingGroupByName(String groupName);
    public void addGroup(Group group);
    public void addUserToGroup(String username, String groupName);
    public void removeRole(UserRole role);
    public void removePermission(Permission permission);
}
