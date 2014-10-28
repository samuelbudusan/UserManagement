package com.evozon.usermanagement.service;

import com.evozon.usermanagement.model.Node;

import java.util.List;

/**
 * Created by samuelbudusan on 10/15/2014.
 */
public interface FileSystemService {

    public List<Node> getAllNodes();
    public String addNode(Node node);
    public Node loadNodeByName(String nodeName);
    public List<Node> getAllRootNodes();
    public void deleteNodes(String[] nodeList);
    public void renameNode(String nodeName, String newName);
    public void cutNodes(String[] nodeList);

}
