package com.evozon.usermanagement.dao.fileSystem;

import com.evozon.usermanagement.model.Node;

import java.util.List;

/**
 * Created by samuelbudusan on 10/15/2014.
 */
public interface FileSystemDAO {

    public void addNode(Node node);
    public void deleteNode(Node node);
    public void updateNodeName(Integer nodeID, String newName);
    public void updateParent(Integer nodeID, Integer parentID);
    public Node findNodeById(Integer nodeID);

}
