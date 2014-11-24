package com.evozon.usermanagement.utils;

import com.evozon.usermanagement.model.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by samuelbudusan on 10/21/2014.
 */
public final class NodeUtil {


    public static List<Node> sortNodeList(List<Node> nodeList) {
        Collections.sort(nodeList, new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {

                return node1.getName().compareTo(node2.getName());
            }
        });
        return nodeList;
    }

    public static String addPath(String json,Node node) {
        String path = node.getName();
        Integer currentNode = node.getNodeID();
        while (node.getParent() != null) {
            if(node.getParent().getNodeID().equals(1)) break;
            path = node.getParent().getName() + "/" + path;
            node = node.getParent();
        }
        return "{ \"data\" :"+ json + " , \"path\" : " + "\"" + path + "\" " + ", \"currentNode\" : " + currentNode  + "}";
    }

    public static boolean nodeExist(Node node) {
        Node parent = node.getParent();
        for (Node currentNode : parent.getNodeList()) {
            if (currentNode.getName().equals(node.getName())) {
                return true;
            }
        }
        return false;
    }

    public static String getChildrenJSON(Node node) {
        String json = "";
        List<Node> nodeList = sortNodeList(new ArrayList<Node>(node.getNodeList()));
        for(Node currentNode : nodeList) {
            json += currentNode.toString() + ", ";
        }
        return json;
    }
    public static String getTree(Node node) {
        List<Node> nodeList = sortNodeList(new ArrayList<Node>(node.getNodeList()));
        String tree = "";
        for(Node currentNode : nodeList) {
            tree = traversBranch(currentNode,tree);
        }
        return tree;
    }

    private static String traversBranch(Node node,String tree) {
        tree += node.toString() + ", ";
        List<Node> nodeList = sortNodeList(new ArrayList<Node>(node.getNodeList()));
        for(Node currentNode : nodeList) {
            tree = traversBranch(currentNode,tree);
        }
        return tree;
    }

    public static Node findNode(Node parent, Integer index) {
        for(Node node : parent.getNodeList()) {
            if( node.getNodeID().equals(index)) {
                return node;
            }
            Node found = findNode(node,index);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public static String childrenJSON(Node parent){
        List<Node> list = sortNodeList(new ArrayList<Node>(parent.getNodeList()));
        String json = "[ ";
        for(Node node : list) {
            json += "{ \"id\" : \"" + node.getNodeID() + "\" , \"name\" : \"" + node.getName() + "\" },";
        }
        json = json.substring(0,json.length()-1);
        json += " ]";
        return json;
    }
}
