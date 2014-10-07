package com.evozon.usermanagement.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by samuelbudusan on 9/30/2014.
 */
@Entity
@Table(name="group", catalog="userdb")
public class Group {

    @Id
    @Column(name = "groupname")
    private String groupName;

    @ManyToMany( fetch = FetchType.EAGER, mappedBy = "groups")
    private Set<User> users = new HashSet<User>(0);

    public Group() {
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public Group(String groupName, Set<User> users) {
        this.groupName = groupName;
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<User> getUsers() {
        return users;
    }

   public void setUsers(Set<User> users) {
        this.users = users;
    }

}
