package com.evozon.usermanagement.utils;

import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.utils.GroupUtil;
import com.evozon.usermanagement.utils.UserUtil;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * Created by samuelbudusan on 10/9/2014.
 */
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        User user = (User) authentication.getPrincipal();
        if(UserUtil.isAdmin(user)) {
            return true;
        }

        Group group = (Group) targetDomainObject;
        if(GroupUtil.isMemberOfGroup(group, user.getUserName())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        throw new UnsupportedOperationException();
    }
}
