package com.evozon.usermanagement.dao.fileSystem;

import com.evozon.usermanagement.model.Node;

import java.util.List;

/**
 * Created by samuelbudusan on 10/15/2014.
 */
public interface FileSystemDAO {

    public List<Node> getAllNodes();
    public void addNode(Node node);
    public Node findNodeByName(String nodeName);
    public void deleteNode(Node node);
    public void updateNodeName(String nodeName, String newName);
    public void updateParrnetName(String nodeName, String parrentName);

}
