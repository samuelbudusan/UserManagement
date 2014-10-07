package com.evozon.usermanagement.controller;

import com.evozon.usermanagement.dao.UserDAO;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.service.EditUserService;
import com.evozon.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EditUsersController {

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

    @PreAuthorize("hasRole('editAccounts')")
    @RequestMapping(value="/editUsers", method = RequestMethod.GET)
    public String editUsers(Model model) {
        List<User> userList = userService.getAllSimpleUsers();
        model.addAttribute("userList",userList);
        return "editUsers";

    }

    @PreAuthorize("hasRole('editAccounts')")
    @RequestMapping(value="/editUserByAdmin", method = RequestMethod.GET)
    public String editSimpleUser(Model model,HttpServletRequest request, HttpSession session) {
        Integer index = Integer.parseInt((String) request.getParameter("index"));
        List<User> userList = userService.getAllSimpleUsers();
        User simpleUser = userList.get(index);
        session.setAttribute("currentUserName", simpleUser.getUserName());
        session.setAttribute("currentPassword", simpleUser.getPassword());
        model.addAttribute("simpleUser", simpleUser);
        return "editUserByAdmin";

    }

    @RequestMapping(value ="/editUserByAdmin", method = RequestMethod.POST)
    public String submitUserInformation(@ModelAttribute User simpleUser, Model model, HttpSession session) {

        String page = "editUserByAdmin";
        String userName = (String) session.getAttribute("currentUserName");
        String password = (String) session.getAttribute("currentPassword");
        simpleUser.setUserName(userName);
        simpleUser.setPassword(password);

        String errors = service.editUserInfo(simpleUser);
        if( errors.equals("") ) {
            model.addAttribute("isOk", 1);
            page =  "redirect:/editUsers";
        }
        else {
            String[] parts = errors.split(",");
            model.addAttribute("simpleUser", simpleUser);
            model.addAttribute("errors", parts);
            model.addAttribute("fail",0);
        }
        return page;
    }

    @PreAuthorize("hasRole('editAccounts')")
    @RequestMapping(value="/disableAccount", method = RequestMethod.GET)
    public String enableOrDisableUserAccount(Model model,HttpServletRequest request, HttpSession session) {

        String page = "editUserByAdmin";
        Integer enable = Integer.parseInt((String) request.getParameter("enable"));
        String userName = (String) session.getAttribute("currentUserName");
        User user = userService.loadUserByUsername(userName);
        user.setEnabled(enable);

        String errors = service.editUserInfo(user);
        if( errors.equals("") ) {
            page =  "redirect:/editUsers";
        }
        else {
            String[] parts = errors.split(",");
            model.addAttribute("simpleUser",user);
            model.addAttribute("errors", parts);
            model.addAttribute("fail",0);
        }
        return page;
    }

    @PreAuthorize("hasRole('editAccounts')")
    @RequestMapping(value="/resetPass", method = RequestMethod.GET)
    public String resetPasswordByAdmin(Model model, HttpSession session) {

        String page = "editUserByAdmin";
        String userName = (String) session.getAttribute("currentUserName");
        User user = userService.loadUserByUsername(userName);
        user.setPassword(BCrypt.hashpw( userName, BCrypt.gensalt()));

        String errors = service.editUserInfo(user);
        if( errors.equals("") ) {
            page =  "redirect:/editUsers";
        }
        else {
            String[] parts = errors.split(",");
            model.addAttribute("simpleUser",user);
            model.addAttribute("errors", parts);
            model.addAttribute("fail",0);
        }
        return page;
    }
}
