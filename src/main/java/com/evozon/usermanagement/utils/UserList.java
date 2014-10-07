package com.evozon.usermanagement.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by samuelbudusan on 10/3/2014.
 */
public class UserList {

    private String groupName;
    private List<String> users = new ArrayList<String>();

    public UserList() {
    }

    public UserList(String groupName, List<String> users, List<String> roles) {
        this.groupName = groupName;
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
