package com.evozon.usermanagement.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * Created by samuelbudusan on 9/27/2014.
 */
@Entity
@Table(name = "permission", catalog = "userdb")
public class Permission implements GrantedAuthority {

    @Id @GeneratedValue
    @Column(name = "permissionID")
    private Integer permissionID;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleID", nullable = false)
    private UserRole userRole;

    public Permission() {
    }

    public Permission(String name) {
        this.name = name;
    }

    public Permission(String name, UserRole userRole) {
        this.name = name;
        this.userRole = userRole;
    }

    public Integer getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(Integer permissionID) {
        this.permissionID = permissionID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}