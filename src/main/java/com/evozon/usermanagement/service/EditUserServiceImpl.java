package com.evozon.usermanagement.service;

import com.evozon.usermanagement.dao.UserDAO;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.utils.UserUtil;
import com.evozon.usermanagement.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditUserServiceImpl implements EditUserService{

	@Autowired
    private UserDAO dao;

	@Autowired
	private Validator<User> validator;

    @Autowired
    private UserService userService;

	/**
     * returneaza un string gol daca validarile sunt ok
	 * returneaza un string cu erorile aferente greselilor
     */
	public String changePassword(User user, String currentPass, String newPass, String confirmPass) {

		List<User> list = dao.getAllUsers();
		int index = UserUtil.findUserIndex(user, list);
		String errors = "";

		if (index != -1){
			if (!(UserUtil.validateEmptyPasswords(currentPass, newPass, confirmPass))) {
				errors += "You must complete all the fields!" + "\n";
			} else {
				if (!(UserUtil.validateCurrentPassword(user, currentPass))) {
					errors += "Invalid current password!" + "\n";
				}
				if (!(UserUtil.validatePasswordsMatching(newPass, confirmPass))) {
					errors += "New password and confirm password do not match!" + "\n";
				}
			}
		} else  {
			errors += "User cannot be found!" + "\n";
		}

		if (errors.equals("")) {
			user.setPassword(BCrypt.hashpw(newPass, BCrypt.gensalt()));
            user.setPassword(BCrypt.hashpw(newPass, BCrypt.gensalt()));
			list.set(index, user);
			dao.updateUsers(list,index);
		}
		return errors;
	}

	@Override
	public String editUserInfo(User user) {
        List<User> list = dao.getAllUsers();
		int index = -1;
		String errors = validator.validate(user);
		if( errors.equals("") ){
			for(User dest : list ) {
				if( user.getUserName().equals(dest.getUserName())) {
					dest.setEmail(user.getEmail());
					dest.setBirthdate(user.getBirthdate());
					dest.setPhone(user.getPhone());
					dest.setFirstName(user.getFirstName());
					dest.setLastName(user.getLastName());
                    if(user.getEnabled() != null ) {
                        dest.setEnabled(user.getEnabled());
                    }
                    dest.setPassword(user.getPassword());
					index= list.indexOf(dest);
				}
			}
			dao.updateUsers(list,index);
		}

		return errors;
	}

	//returneaza un string gol daca validarile sunt ok
	//returneaza un string cu erorile aferente greselilor	
	//daca e ok reseteaza parola
	public String resetPassword(String email) {
		List<User> list = dao.getAllUsers();
		String errors = "";
		User user = userService.loadUserByEmail(email);

		if (!UserUtil.validateEmptyField(email)) {
			errors += "Complete the email field!";
		} else {
			if (!UserUtil.validateEmail(email)) {
				errors += "The email must contain @!";
			} else {	
				if (user == null) {
					errors += "This email cannot be found!";	
				}
			}

			if (errors.equals("")) {
				int index = UserUtil.findUserIndex(user, list);
				user.setPassword(BCrypt.hashpw(UserUtil.getGeneratedPassword(), BCrypt.gensalt()));
				list.set(index, user);
				dao.updateUsers(list,index);
			}
		}
		return errors; 
	}

}
