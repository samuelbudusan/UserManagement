package com.evozon.usermanagement.controller;

import com.evozon.usermanagement.model.Node;
import com.evozon.usermanagement.service.FileSystemService;
import com.evozon.usermanagement.utils.Memory;
import com.evozon.usermanagement.utils.NodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuelbudusan on 10/15/2014.
 */

@Controller
public class FileSystemController {

    @Autowired
    FileSystemService fileSystemService;

    @RequestMapping(value= { "/" }, method = RequestMethod.GET)
    public String test() {
        return "redirect:/fileSystem?node=root";
    }


    @RequestMapping(value= { "/fileSystem" }, method = RequestMethod.GET)
    public String fileSystem(Model model, HttpServletRequest request) {

        Node node = fileSystemService.loadNodeByName(request.getParameter("node"));
        List<Node> nodeList = NodeUtil.sortNodeList(new ArrayList<Node>(node.getNodeList()));
        model.addAttribute("nodeList", nodeList);
        model.addAttribute("currentNode", node);
        model.addAttribute("errors",request.getParameter("errors"));
        model.addAttribute("nodePath", NodeUtil.getPath(node));
        model.addAttribute("selectedNodes", Memory.getMemory());
        return "fileSystem";
    }

    @RequestMapping(value = "/newNode", method = RequestMethod.GET)
    public String newNode(HttpServletRequest request){
        String currentNode = request.getParameter("node");
        String newNode = request.getParameter("newNode");
        Node parent = fileSystemService.loadNodeByName(currentNode);
        Node node = new Node(newNode,parent);
        String errors = fileSystemService.addNode(node);
        return "redirect:/fileSystem?node="+ currentNode + "&errors=" + errors;
    }

    @RequestMapping(value = "/deleteNode", method = RequestMethod.GET)
    public String deleteNode(HttpServletRequest request){
        String currentNode = request.getParameter("node");
        String[] nodeList = request.getParameter("nodeList").split(",");
        fileSystemService.deleteNodes(nodeList);
        return "redirect:/fileSystem?node="+currentNode;
    }

    @RequestMapping(value = "/renameNode", method = RequestMethod.GET)
    public String renameNode(HttpServletRequest request){
        String errors = "";
        String currentNode = request.getParameter("node");
        String nodeName  = request.getParameter("nodeName");
        String newName = request.getParameter("newName");
        if( !newName.equals("") ) {
            fileSystemService.renameNode(nodeName,newName);
        } else {
            errors = "Name cannot be empty!";
        }
        //TODO verif the name to not be empty
        return "redirect:/fileSystem?node="+currentNode + "&errors=" + errors;
    }

    @RequestMapping(value = "/selectedItems", method = RequestMethod.GET)
    public String getSelectedItems(HttpServletRequest request){
        String errors = "";
        String currentNode = request.getParameter("node");
        String[] nodeList = request.getParameter("nodeList").split(",");


        return "redirect:/fileSystem?node="+currentNode + "&errors=";
    }

    @RequestMapping(value = "/cutNode", method = RequestMethod.GET)
    public String cutNode(HttpServletRequest request){
        String currentNode = request.getParameter("node");
        String[] nodeList = request.getParameter("nodeList").split(",");
        fileSystemService.cutNodes(nodeList);
        return "redirect:/fileSystem?node="+currentNode;
    }

    @RequestMapping(value = "/cancelOperation", method = RequestMethod.GET)
    public String cancelOperation(HttpServletRequest request){
        String currentNode = request.getParameter("node");
        Memory.clearMemory();
        return "redirect:/fileSystem?node="+currentNode;
    }

}
