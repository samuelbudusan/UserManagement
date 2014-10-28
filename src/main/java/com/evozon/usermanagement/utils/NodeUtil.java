package com.evozon.usermanagement.utils;

import com.evozon.usermanagement.model.Node;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by samuelbudusan on 10/21/2014.
 */
public final class NodeUtil {

    public static String previousPath(String path) {
        String previousPath = "";
        String parts[] = path.split("/");
        for (String part : parts) {
            if (!part.equals(parts[parts.length - 1]) && !part.equals("")) {
                previousPath = previousPath + "/" + part;
            }
        }
        if (previousPath.equals("")) {
            previousPath = "/success";
        }
        return previousPath;
    }

    public static List<Node> sortNodeList(List<Node> nodeList) {
        Collections.sort(nodeList, new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {

                return node1.getName().compareTo(node2.getName());
            }
        });
        return nodeList;
    }

    public static String getPath(Node node) {
        String path = node.getName();

        while (node.getParent() != null) {
            path = node.getParent().getName() + "/" + path;
            node = node.getParent();
        }
        return path;
    }
}
