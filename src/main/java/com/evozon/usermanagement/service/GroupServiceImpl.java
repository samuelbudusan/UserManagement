package com.evozon.usermanagement.service;

import com.evozon.usermanagement.dao.UserDAO;
import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.Permission;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.model.UserRole;
import com.evozon.usermanagement.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by samuelbudusan on 9/30/2014.
 */
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private UserDAO dao;

    public List<Group> getAllGroups() {
        return dao.getAllGroups();
    }

    @Transactional
    public Group loadGroupByName(String groupName) {
        return dao.findGroupByName(groupName);
    }

    public void addUsersToGroup(List<String> users, String groupName) {
        if( users.size() > 0) {
            for(String user : users) {
                dao.addUserToGroup(user,groupName);
                UserRole role = new UserRole(dao.findByUserName(user),"ROLE_USER",groupName);
                dao.addRole(role);
                Set<Permission> permissions = UserUtil.createPermissionList(role);
                dao.addPermissions(permissions);
            }
        }
    }

    public void removeUserFromGroup(User user, String groupName) {
        for(UserRole userRole : user.getUserRole()) {
            if( !userRole.getRole().equals("ROLE_COMMON")) {
                if(userRole.getGroupName().equals(groupName)) {
                    for(Permission permission : userRole.getUserPermission()) {
                        dao.removePermission(permission);
                    }
                    dao.removeRole(userRole);
                }
            }
        }
        dao.removeUserFromGroup(user.getUserName(),groupName);
    }

    @PreAuthorize("hasAnyRole('groupAccess', 'groupsManagement') and hasPermission(#group, 'groupAccess')")
    public boolean enterGroup(Group group){
        return true;
    }
}
