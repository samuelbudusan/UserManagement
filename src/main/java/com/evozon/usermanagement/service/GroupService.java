package com.evozon.usermanagement.service;

import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.User;

import java.util.List;

/**
 * Created by samuelbudusan on 9/30/2014.
 */
public interface GroupService {

    public List<Group> getAllGroups();
    public Group loadGroupByName(String groupName);
    public void addUsersToGroup(List<String> users, String groupName);
    public void removeUserFromGroup(User user, String groupName);
    public boolean enterGroup(Group group);
}
