package com.evozon.usermanagement.service;

import com.evozon.usermanagement.model.Node;

import java.util.List;

/**
 * Created by samuelbudusan on 10/15/2014.
 */
public interface FileSystemService {

    public String addNode(Node node);
    public void deleteNodes(String[] nodeList);
    public void renameNode(Integer nodeID, String newName);
    public void addNodesToMemory(String[] nodeList,String operation);
    public Node loadNodeById(Integer nodeID);
    public void paste(Integer nodeID);
    public String createJSON(Node node, String direction);

}
