package com.evozon.usermanagement.controller;

import com.evozon.usermanagement.model.Node;
import com.evozon.usermanagement.service.FileSystemService;
import com.evozon.usermanagement.utils.Memory;
import com.evozon.usermanagement.utils.NodeUtil;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.io.Writer;
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
        return "redirect:/fileSystem?node=1";
    }


    @RequestMapping(value= { "/fileSystem" }, method = RequestMethod.GET)
    public String fileSystem(Model model, HttpServletRequest request) {
        Node root = fileSystemService.loadNodeById(1);
        Memory.setRootNode(root);
        String tree = NodeUtil.getTree(root );
        model.addAttribute("tree",tree);
        List<Node> nodeList = NodeUtil.sortNodeList(new ArrayList<Node>(root.getNodeList()));
        model.addAttribute("nodeList", nodeList);
        return "fileSystem";
    }

    @RequestMapping(value = "/newNode", method = RequestMethod.GET)
    public String newNode(HttpServletRequest request){
        Integer currentNode =  Integer.parseInt(request.getParameter("node"));
        String newNode = request.getParameter("newNode");
        Node parent = fileSystemService.loadNodeById(currentNode);
        Node node = new Node(newNode,parent);
        String errors = fileSystemService.addNode(node);
        return "redirect:/fileSystem?errors=" + errors;
    }

    @RequestMapping(value = "/deleteNode", method = RequestMethod.GET)
    public String deleteNode(HttpServletRequest request){
        String[] nodeList = request.getParameter("nodeList").split(",");
        fileSystemService.deleteNodes(nodeList);
        return "redirect:/fileSystem";
    }

    @RequestMapping(value = "/renameNode", method = RequestMethod.GET)
    public String renameNode(HttpServletRequest request){
        String errors = "";
        String currentNode = request.getParameter("node");
        Integer nodeID  = Integer.parseInt(request.getParameter("nodeID"));
        String newName = request.getParameter("newName");
        if( !newName.equals("") ) {
            fileSystemService.renameNode(nodeID,newName);
        } else {
            errors = "Name cannot be empty!";
        }
        return "redirect:/fileSystem?errors=" + errors;
    }

    @RequestMapping(value = "/addNodesToMemory", method = RequestMethod.GET)
    public @ResponseBody String addNodesToMemory(HttpServletRequest request) throws Exception {
        String operation = request.getParameter("operation");
        String[] nodeList = request.getParameter("nodeList").split(",");
        fileSystemService.addNodesToMemory(nodeList,operation);
        return "";
    }

    @RequestMapping(value = "/cancelOperation", method = RequestMethod.GET)
    public String cancelOperation(HttpServletRequest request){
        String currentNode = request.getParameter("node");
        Memory.clearMemory();
        return "redirect:/fileSystem?node="+currentNode;
    }

    @RequestMapping(value = "/paste", method = RequestMethod.GET)
    public String paste(HttpServletRequest request){
        Integer currentNode = Integer.parseInt(request.getParameter("node"));
        fileSystemService.paste(currentNode);
        return "redirect:/fileSystem?node="+currentNode;
    }

    @RequestMapping( value= "/getChildren" , method = RequestMethod.GET)
    public @ResponseBody String getChildren(HttpServletRequest request) throws Exception {
        Integer index = Integer.parseInt(request.getParameter("node"));
        String direction = request.getParameter("direction");
        Node node = NodeUtil.findNode(Memory.getRootNode(),index);
        //Node node = fileSystemService.loadNodeById(index);
        String json = fileSystemService.createJSON(node,direction);
        return json;
    }

}
