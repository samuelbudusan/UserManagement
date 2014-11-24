package com.evozon.usermanagement.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by samuelbudusan on 10/14/2014.
 */

@Entity
@Table(name="node", catalog="userdb")
public class Node implements Serializable {


    @Id @GeneratedValue
    @Column(name = "nodeID")
    private Integer nodeID;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent", nullable = true)
    private Node parent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Node> nodeList = new HashSet<Node>();

    public Node() {
    }

    public Node(String name) {
        this.name = name;
    }

    public Node(String groupName, Node parent) {
        this.name = groupName;
        this.parent = parent;
    }

    public Node(String groupName, Node parent, Set<Node> groupList) {
        this.name = groupName;
        this.parent = parent;
        this.nodeList = groupList;
    }

    public Integer getNodeID() {
        return nodeID;
    }

    public void setNodeID(Integer nodeID) {
        this.nodeID = nodeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Set<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(Set<Node> nodeList) {
        this.nodeList = nodeList;
    }

    @Override
    public String toString() {
        String currentParent = "";
        if(parent == null ) return "";
        if(parent.getNodeID() == 1) {
            currentParent="#";
        } else {
            currentParent = ""+this.parent.getNodeID();
        }
        return  "{ 'id' : '" + nodeID +
                "' , 'parent' : '" + currentParent +
                "' , 'text' : '" + name +"' }";
    }
}
