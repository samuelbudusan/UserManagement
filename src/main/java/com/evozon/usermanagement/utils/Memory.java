package com.evozon.usermanagement.utils;

import com.evozon.usermanagement.model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuelbudusan on 10/27/2014.
 */
public final class Memory {

    private static List<Integer> memory = new ArrayList<Integer>();

    private static String operation;

    private static Node rootNode;

    public static List<Integer> getMemory() {
        return memory;
    }

    public static void setMemory(List<Integer> memory) {
        Memory.memory = memory;
    }

    public static void addNode(Integer nodeID) {
        memory.add(nodeID);
    }

    public static void addNodes(String[] nodeList) {
        for(String node : nodeList) {
            memory.add(Integer.parseInt(node));
        }
    }

    public static String getOperation() {
        return operation;
    }

    public static void setOperation(String operation) {
        Memory.operation = operation;
    }

    public static void clearMemory() {
        memory.clear();
        operation = "";
    }

    public static Node getRootNode() {
        return rootNode;
    }

    public static void setRootNode(Node rootNode) {
        Memory.rootNode = rootNode;
    }
}
