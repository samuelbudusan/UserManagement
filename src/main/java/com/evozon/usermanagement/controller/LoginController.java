package com.evozon.usermanagement.controller;

import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.service.GroupService;
import com.evozon.usermanagement.service.UserService;
import com.evozon.usermanagement.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

@Controller
@SessionAttributes({"user"})
public class LoginController {

    @Autowired
    GroupService groupService;

	@RequestMapping(value = { "/login", "/"}, method = RequestMethod.GET)
	public String Login(@RequestParam(required = false) Boolean successfulAccount, Model model) {
		model.addAttribute("successfulAccount", successfulAccount);
		return "login";
	}

    @RequestMapping(value="/loginError", method = RequestMethod.GET)
    public String loginError(Model model, HttpServletRequest request) {
        Exception e = (Exception) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        model.addAttribute("error", e.getMessage());
        return "login";
    }

    @PreAuthorize("hasRole('common')")
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String requestSuccessPage(@RequestParam(required = false) Integer isOk, Model model, HttpServletRequest request){

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        model.addAttribute("userName", user.getUserName());
        model.addAttribute("isOk", isOk);
        if(UserUtil.isAdmin(user)) {
            model.addAttribute("isAdmin", 1);
        }

        List<Group> groupList = groupService.getAllGroups();
        model.addAttribute("groupList", groupList);
        return "success";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model) {
        model.addAttribute("httpStatusMessage", "HTTP Status 403 - Access is denied");
        model.addAttribute("msg","You do not have permission to access this resources!");
        return "accessDenied";
    }
}
