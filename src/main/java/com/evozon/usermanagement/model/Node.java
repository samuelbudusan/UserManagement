package com.evozon.usermanagement.model;

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

    @Id
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent", referencedColumnName="name" ,nullable = true)
    private Node parent;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
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


}
