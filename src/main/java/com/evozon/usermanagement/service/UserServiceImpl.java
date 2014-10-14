package com.evozon.usermanagement.service;

import com.evozon.usermanagement.dao.UserDAO;
import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.Permission;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.model.UserRole;
import com.evozon.usermanagement.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.Role;

/**
 * Created by samuelbudusan on 9/24/2014.
 */
@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserDAO dao;

    @Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return dao.findByUserName(username);
    }

    public User loadUserByEmail(String email) {
        List<User> list = dao.getAllUsers();

        for (User dest : list) {
            if (email.equals(dest.getEmail())) {
                return dest;
            }
        }
        return null;
    }

    public List<User> getAllSimpleUsers() {
        return dao.getAllSimpleUsers();
    }

    public void changeRole(User user, String groupName, String moderator) {

        UserRole role = new UserRole(user, "ROLE_MODERATOR", groupName);
        if (moderator.equals("true")) {
            dao.addRole(role);
            Set<Permission> permissions = UserUtil.createPermissionList(role);
            dao.addPermissions(permissions);

        } else if (moderator.equals("false")) {
            for (UserRole userRole : user.getUserRole()) {
                if (userRole.getRole().equals("ROLE_MODERATOR")) {
                    if (userRole.getGroupName().equals(groupName)) {
                        for (Permission permission : userRole.getUserPermission()) {
                            dao.removePermission(permission);
                        }
                        dao.removeRole(userRole);
                    }
                }
            }

        }
    }
}