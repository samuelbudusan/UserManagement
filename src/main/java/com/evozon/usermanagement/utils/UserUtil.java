package com.evozon.usermanagement.utils;

import com.evozon.usermanagement.model.Permission;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.model.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by samuelbudusan on 9/24/2014.
 */
public final class UserUtil {

    public static String getGeneratedPassword() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(64, random).toString(32);
    }

    public static int findUserIndex(User user, List<User> list) {
        int index = 0;
        for (User dest : list) {
            if (user.equals(dest))
                return index;
            index ++;
        }
        return -1;
    }

    public static boolean validateCurrentPassword(User user, String currentPass) {
        if (BCrypt.checkpw(currentPass, user.getPassword())) {
            return true;
        }
        return false;
    }

    public static boolean validatePasswordsMatching(String newPass, String confirmPass) {
        if (newPass.equals(confirmPass)) {
            return true;
        }
        return false;
    }

    public static boolean validateEmptyPasswords(String currentPass, String newPass, String confirmPass) {
        if (currentPass.equals("") || newPass.equals("") || confirmPass.equals("")) {
            return false;
        }
        return true;
    }

    public static boolean validateEmptyField(String field){
        if (field.equals("")) {
            return false;
        }
        return true;
    }

    public static boolean validateEmail(String email) {
        if (email.contains("@")) {
            return true;
        }
        return false;
    }

    public static Set<Permission> createPermissionList(UserRole role) {
        Set<Permission> permissions = new HashSet<Permission>();
        if( role.getRole().equals("ROLE_COMMON")) {
            permissions.add(new Permission("common",role));
        } else
        if(role.getRole().equals("ROLE_ADMIN")) {
            permissions.add(new Permission("editAccounts",role));
            permissions.add(new Permission("groupsManagement",role));
        } else
        if(role.getRole().equals("ROLE_USER")) {
            permissions.add(new Permission("groupAccess",role));
        } else
        if(role.getRole().equals("ROLE_MODERATOR")) {
            permissions.add(new Permission("kickUser",role));
        }
        return permissions;
    }

    public static List<GrantedAuthority> buildUserPermissions(Set<UserRole> userRoles) {

        Set<GrantedAuthority> setPermissions = new HashSet<GrantedAuthority>();

        for (UserRole userRole : userRoles) {
            for(Permission permission : userRole.getUserPermission()) {
                if( !setPermissions.contains(permission)) {
                    setPermissions.add(new SimpleGrantedAuthority(permission.getAuthority()));
                }
            }
        }
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setPermissions);
        return result;
    }

    public static boolean isAdmin(User user) {
        for(UserRole role : user.getUserRole()) {
            if(role.getRole().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isModerator(User user, String groupName) {
        for(UserRole role : user.getUserRole()) {
            if(role.getGroupName() != null ) {
                if(role.getGroupName().equals(groupName) && role.getRole().equals("ROLE_MODERATOR")) {
                    return true;
                }
            }
        }
        return false;
    }
}
