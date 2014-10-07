package com.evozon.usermanagement.controller;

import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.utils.UserList;
import com.evozon.usermanagement.service.GroupService;
import com.evozon.usermanagement.service.NewItemService;
import com.evozon.usermanagement.service.UserService;
import com.evozon.usermanagement.utils.GroupUtil;
import com.evozon.usermanagement.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuelbudusan on 9/30/2014.
 */

@Controller
public class GroupsController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private NewItemService newItemService;

    @PreAuthorize("hasRole('groupsManagement')")
    @RequestMapping(value = "/groupsManagement", method = RequestMethod.GET)
    public String groupsManagement(Model model){
        List<Group> groupList = groupService.getAllGroups();
        model.addAttribute("groupList", groupList);
        return "groups";
    }

    @PreAuthorize("hasRole('groupsManagement')")
    @RequestMapping(value = "/editGroup", method = RequestMethod.GET)
    public String editGroup(Model model, HttpServletRequest request, HttpSession session){
        Integer index = Integer.parseInt((String) request.getParameter("index"));
        Group group = groupService.getAllGroups().get(index);
        session.setAttribute("currentGroup",index);
        List<User> groupMembers = new ArrayList<User>(group.getUsers());
        List<User> allUsers = GroupUtil.getUsersExceptGroupMembers(userService.getAllSimpleUsers(), group);
        model.addAttribute("group", group);
        model.addAttribute("groupMembers", groupMembers);
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("addedUsers",  new UserList());
        return "editGroup";
    }

    @PreAuthorize("hasRole('groupsManagement')")
    @RequestMapping(value = "/newGroup", method = RequestMethod.GET)
    public String newGroup(Model model){
        Group group = new Group();
        model.addAttribute("group", group);
        return "newGroup";
    }

    @RequestMapping(value = "/newGroup", method = RequestMethod.POST)
    public String submitNewGroup(@ModelAttribute Group group, Model model){
        String errors = newItemService.addGroup(group);

        if ( errors.equals("") ) {
            return "redirect:/groupsManagement";
        } else {
            model.addAttribute("errors",errors);
            model.addAttribute("fail",0);
            return "newGroup";
        }
    }

    @PreAuthorize("hasRole('groupsManagement')")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUserToGroup(@ModelAttribute UserList list){
        Integer index = GroupUtil.getIndexOfGroup(groupService.getAllGroups(),list.getGroupName());
        groupService.addUsersToGroup(list.getUsers(),list.getGroupName());
        return "redirect:/editGroup?index="+index;
    }

    @PreAuthorize("hasRole('groupsManagement')")
    @RequestMapping(value = "/changeRole", method = RequestMethod.GET)
    public String changeRole(HttpServletRequest request , Model model, HttpSession session){
        Integer index = Integer.parseInt((String) request.getParameter("index"));
        Group group = groupService.loadGroupByName(request.getParameter("groupname"));
        List<User> groupMembers = new ArrayList<User>(group.getUsers());
        User simpleUser = groupMembers.get(index);
        session.setAttribute("currentUserName", simpleUser.getUserName());
        boolean bool = UserUtil.isModerator(simpleUser,group.getGroupName());
        model.addAttribute("user", simpleUser);
        model.addAttribute("isModerator", bool );
        return "changeRole";
    }

    @PreAuthorize("hasRole('groupsManagement')")
    @RequestMapping(value="/addOrRemoveRole", method = RequestMethod.GET)
    public String addOrRemoveRole(HttpServletRequest request, HttpSession session) {
        String moderator =  request.getParameter("moderator");
        String userName = (String) session.getAttribute("currentUserName");
        User user = userService.loadUserByUsername(userName);
        Group group = groupService.getAllGroups().get((Integer) session.getAttribute("currentGroup"));
        userService.changeRole(user,group.getGroupName(), moderator);
        return "redirect:/editGroup?index="+session.getAttribute("currentGroup");
    }

}
