package com.evozon.usermanagement.hibernate;

import com.evozon.usermanagement.dao.fileSystem.FileSystemDAO;
import com.evozon.usermanagement.model.Node;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by samuelbudusan on 10/15/2014.
 */

@Repository
public class FileSystemDAOImpl implements FileSystemDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Node findNodeById(Integer nodeID) {
      /*  Node node = (Node) sessionFactory.getCurrentSession().get(Node.class, nodeID);
        return node;*/
        List<Node> nodes = sessionFactory.getCurrentSession().createQuery("from Node n where n.nodeID=" + nodeID).list();
        if(nodes.size() > 0) {
            return (Node) nodes.get(0);
        }
        return null;

    }

    public void addNode(Node node) {
        sessionFactory.openSession();
        Session s = sessionFactory.getCurrentSession();
        Transaction tx = s.beginTransaction();
        s.save(node);
        tx.commit();
        s.flush();
    }

    public void deleteNode(Node node) {
        Session s = sessionFactory.getCurrentSession();
        Transaction tx = s.beginTransaction();
        s.delete(node);
        tx.commit();
        s.flush();
    }

    public void updateNodeName(Integer nodeID, String newName) {
        String sql = "UPDATE node SET `name` = '"+ newName +"' WHERE `nodeId` =" + nodeID;
        Session s = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.createSQLQuery(sql).executeUpdate();
            if (!tx.wasCommitted()) {
                tx.commit();
            }
        } catch (Exception exp) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    public void updateParent(Integer nodeID, Integer parentID) {
        String sql = "UPDATE node SET `parent` = '"+ parentID +"' WHERE `nodeId` =" + nodeID;
        Session s = sessionFactory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = s.beginTransaction();
            s.createSQLQuery(sql).executeUpdate();
            if (!tx.wasCommitted()) {
                tx.commit();
            }
        } catch (Exception exp) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}
