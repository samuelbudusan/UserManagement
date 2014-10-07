package com.evozon.usermanagement.controller;

import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.service.EditUserService;
import com.evozon.usermanagement.service.UserService;
import com.evozon.usermanagement.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@SessionAttributes({"user"})
public class EditUserController {

	@Autowired
    EditUserService service;

    @Autowired
    UserService userService;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
	 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 dateFormat.setLenient(false);
	 webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	 }

    @PreAuthorize("hasRole('common')")
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String getEditForm(Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");
		model.addAttribute("destUser", user);
		
		return "edit";	
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editUserInformation(@ModelAttribute User destUser, Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");
        user.setEmail(destUser.getEmail());
        user.setBirthdate(destUser.getBirthdate());
        user.setPhone(destUser.getPhone());
        user.setFirstName(destUser.getFirstName());
        user.setLastName(destUser.getLastName());
        session.setAttribute("user",user);
		String page = "edit";
	
		String errors = service.editUserInfo(user);
		if( errors.equals("") ) {
			model.addAttribute("isOk", 1);
			page =  "redirect:/success";
		}
		else {
			String[] parts = errors.split(",");
            model.addAttribute("destUser", user);
			model.addAttribute("errors", parts);
			model.addAttribute("fail",0);
		}
		
		return page;

	}

    @PreAuthorize("hasRole('common')")
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String getChangePasswordForm() {
		return "changePassword";
		
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePasswordSubmit(@RequestParam String currentPassword, @RequestParam String newPassword, @RequestParam String confirmPassword,Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");
		String page = "changePassword";
		String errors = service.changePassword(user, currentPassword, newPassword, confirmPassword);

		if (errors.equals("")) {
			model.addAttribute("isOk", 0);
			page = "redirect:/success";
		} else {
			model.addAttribute("fail", 0);
			model.addAttribute("errors", errors);
		}

		return page;
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	 public String getResetPasswordForm(Model model) {
		User user = new User();
		model.addAttribute("user",user);
		return "resetPassword";
	 }

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public String resetPasswordSubmit(Model model, @RequestParam String email, @ModelAttribute User user){

		String page = "resetPassword";
		String errors = service.resetPassword(email);

		if (errors.equals("")) {
			User userByEmail = userService.loadUserByEmail(email);
			page = "resetPasswordOk";
			model.addAttribute("newPassword", userByEmail.getPassword());
			model.addAttribute("newPassword", UserUtil.getGeneratedPassword());
		} else {
			model.addAttribute("fail", 0);
			model.addAttribute("errors", errors);
		}

		return page;
	}


}
