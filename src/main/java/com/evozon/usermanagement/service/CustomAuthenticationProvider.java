package com.evozon.usermanagement.service;
import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.UserRole;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = userService.loadUserByUsername(username);

        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }

        if(!BCrypt.checkpw( password , user.getPassword()) ) {
            throw new BadCredentialsException("Wrong password.");
        }

        if( user.getEnabled() == 0 ) {
            throw new BadCredentialsException("Your account is locked.");
        }

        Collection<? extends GrantedAuthority> permissions = UserUtil.buildUserPermissions(user.getUserRole());

        return new UsernamePasswordAuthenticationToken(user, password, permissions);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}

