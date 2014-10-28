package com.evozon.usermanagement.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuelbudusan on 10/27/2014.
 */
public final class Memory {

    private static List<String> memory = new ArrayList<String>();

    public static List<String> getMemory() {
        return memory;
    }

    public static void setMemory(List<String> memory) {
        Memory.memory = memory;
    }

    public static void addNode(String nodeName) {
        memory.add(nodeName);
    }

    public static void addNodes(String[] nodeList) {
        for(String node : nodeList) {
            memory.add(node);
        }
    }

    public static void clearMemory() {
        memory.clear();
    }

}
