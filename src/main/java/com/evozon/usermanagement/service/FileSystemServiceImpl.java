package com.evozon.usermanagement.service;

import com.evozon.usermanagement.dao.fileSystem.FileSystemDAO;
import com.evozon.usermanagement.model.Node;
import com.evozon.usermanagement.utils.Memory;
import com.evozon.usermanagement.utils.NodeUtil;
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

        if( !NodeUtil.nodeExist(node)) {
            fileSystemDAO.addNode(node);
        } else {
            errors = "The given node name is already in use in this folder.";
        }
        return errors;
    }

    @Transactional
    public Node loadNodeById(Integer nodeID) {
        return fileSystemDAO.findNodeById(nodeID);
    }

    public void deleteNodes(String[] nodeList) {
        Node currentNode;
        for(String node : nodeList) {
            currentNode = fileSystemDAO.findNodeById(Integer.parseInt(node));
            delete(currentNode);
        }
    }

    private void delete(Node currentNode) {
        for(Node node: currentNode.getNodeList() ) {
            delete(node);
        }
        fileSystemDAO.deleteNode(currentNode);
    }

    public void renameNode(Integer nodeID, String newName) {
        Node node = loadNodeById(nodeID);
        fileSystemDAO.updateNodeName(node.getNodeID(),newName);
    }

    public void addNodesToMemory(String[] nodeList,String operation) {
        Memory.clearMemory();
        Memory.addNodes(nodeList);
        Memory.setOperation(operation);
    }

    public void paste(Integer nodeID) {
        switch(Memory.getOperation()) {
            case "copy" : copyMainNode(nodeID); break;
            case "cut" : cut(nodeID); break;
        }
    }

    private void cut(Integer nodeID) {
        Node node = fileSystemDAO.findNodeById(nodeID);
        List<Integer> nodeList = Memory.getMemory();
        boolean paste = true;
        for(Integer id : nodeList) {
            for(Node childNode : node.getNodeList()) {
                if( childNode.getName().equals(fileSystemDAO.findNodeById(id).getName()) ) {
                    paste = false;
                }
            }
            if(paste) {
                fileSystemDAO.updateParent(id,node.getNodeID());
            }
            paste = true;
        }
        Memory.clearMemory();
    }

    private void copyMainNode(Integer nodeID) {
        Node node = fileSystemDAO.findNodeById(nodeID);
        Node newNode;
        List<Integer> nodeList = Memory.getMemory();
        boolean paste = true;
        for(Integer id : nodeList) {
            for(Node childNode : node.getNodeList()) {
                if( childNode.getName().equals(fileSystemDAO.findNodeById(id).getName()) ) {
                    paste = false;
                }
            }
            if(paste) {
                newNode = fileSystemDAO.findNodeById(id);
                newNode.setParent(node);
                //fileSystemDAO.addNode(newNode);
                copy(newNode);
            }
            paste = true;
        }
        Memory.clearMemory();
    }

    private void copy(Node parentNode){
        fileSystemDAO.addNode(parentNode);
        for(Node node : parentNode.getNodeList()) {
           copy(node);
        }
    }

    public String createJSON(Node node, String direction) {
        String json = null;
        if(node == null ) {
            json = NodeUtil.addPath(NodeUtil.childrenJSON(Memory.getRootNode()),Memory.getRootNode());
        } else {
            switch(direction) {
                case "forward" : json = NodeUtil.addPath(NodeUtil.childrenJSON(node),node); break;
                case "backward" : json = NodeUtil.addPath(NodeUtil.childrenJSON(node.getParent()),node.getParent()); break;
            }
        }
        return json;
    }
}

