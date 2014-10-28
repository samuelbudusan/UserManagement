package com.evozon.usermanagement.service;

import com.evozon.usermanagement.dao.fileSystem.FileSystemDAO;
import com.evozon.usermanagement.model.Node;
import com.evozon.usermanagement.utils.Memory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by samuelbudusan on 10/15/2014.
 */

@Service
public class FileSystemServiceImpl implements FileSystemService {

    @Autowired
    FileSystemDAO fileSystemDAO;

    public String addNode(Node node) {
        String errors = "";
        if (fileSystemDAO.findNodeByName(node.getName()) == null) {
            fileSystemDAO.addNode(node);
        } else {
            errors = "The given node name is already in use.";
        }
        return errors;
    }

    public List<Node> getAllNodes() {
        return fileSystemDAO.getAllNodes();
    }

    public List<Node> getAllRootNodes() {
        List<Node> allNodes = fileSystemDAO.getAllNodes();
        List<Node> rootNodes = new ArrayList<Node>();
        for (Node node :allNodes) {
            if(node.getParent() == null) {
                rootNodes.add(node);
            }
        }
        return rootNodes;
    }

    @Transactional
    public Node loadNodeByName(String nodeName) {
        return fileSystemDAO.findNodeByName(nodeName);
    }

    public void deleteNodes(String[] nodeList) {
        Node currentNode;
        for(String nodeName : nodeList) {
            currentNode = fileSystemDAO.findNodeByName(nodeName.trim());
            delete(currentNode);
        }
    }

    private void delete(Node currentNode) {
        for(Node node: currentNode.getNodeList() ) {
            delete(node);
        }
        fileSystemDAO.deleteNode(currentNode);
    }

    public void renameNode(String nodeName, String newName) {
        Node parrentNode = loadNodeByName(nodeName);
        for(Node node : parrentNode.getNodeList()) {
            fileSystemDAO.updateParrnetName(node.getName(),newName);
        }
        fileSystemDAO.updateNodeName(nodeName,newName);
    }

    public void cutNodes(String[] nodeList) {
        Memory.clearMemory();
        Memory.addNodes(nodeList);
    }


}

