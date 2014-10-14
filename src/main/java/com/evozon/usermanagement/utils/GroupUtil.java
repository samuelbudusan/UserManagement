package com.evozon.usermanagement.utils;

import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.model.UserRole;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuelbudusan on 10/2/2014.
 */
public final class GroupUtil {

    public static List<User> getUsersExceptGroupMembers(List<User> users,Group group) {

        List<User> groupMembers = new ArrayList<User>(group.getUsers());
        List<User> result = new ArrayList<User>();
        for(User user : users) {
            if( !groupMembers.contains(user)) {
                result.add(user);
            }
        }
        return result;
    }

    public static Integer getIndexOfGroup(List<Group> groupList,String userName) {
        Integer index = 0;
        for(Group group : groupList ) {
            if (group.getGroupName().equals(userName)) {
                return index;
            } else {
                index++;
            }
        }
        return -1;
    }

    public static boolean isMemberOfGroup(Group group, String userName) {
        for(User user :group.getUsers()) {
            if(user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
}
