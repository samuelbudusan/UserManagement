package com.evozon.usermanagement.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by samuelbudusan on 9/16/2014.
 */

@Entity
@Table(name = "user_roles", catalog="userdb" )
public class UserRole {

    private Integer roleID;
    private User user;
    private String role;
    private String groupName;
    private Set<Permission> userPermission = new HashSet<Permission>(0);

    public UserRole() {
    }

    public UserRole(User user, String role, String groupName) {
        this.user = user;
        this.role = role;
        this.groupName = groupName;
    }

    public UserRole(User user, String role, String groupName, Set<Permission> userPermission) {
        this.user = user;
        this.role = role;
        this.groupName = groupName;
        this.userPermission = userPermission;
    }

    @Id @GeneratedValue
    @Column(name = "roleId")
    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "role", nullable = false)
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "groupName", nullable = true)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userRole", targetEntity = Permission.class)
    @Fetch(FetchMode.SUBSELECT)
    public Set<Permission> getUserPermission() {
        return userPermission;
    }

    public void setUserPermission(Set<Permission> userPermission) {
        this.userPermission = userPermission;
    }
}
