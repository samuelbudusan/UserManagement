package com.evozon.usermanagement.service;

import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.UserRole;
import com.evozon.usermanagement.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * Created by samuelbudusan on 9/24/2014.
 */
public interface UserService {

    public User loadUserByUsername(String username) throws UsernameNotFoundException;
    public User loadUserByEmail(String email);
    public List<User> getAllSimpleUsers();
    public void changeRole(User user, String groupName, String isModerator);
}
