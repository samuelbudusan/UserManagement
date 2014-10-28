package com.evozon.usermanagement.hibernate;

import com.evozon.usermanagement.dao.fileSystem.FileSystemDAO;
import com.evozon.usermanagement.model.Node;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.*;
import java.util.List;

/**
 * Created by samuelbudusan on 10/15/2014.
 */

@Repository
public class FileSystemDAOImpl implements FileSystemDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Node> getAllNodes() {
        return sessionFactory.getCurrentSession().createQuery("from Node").list();
    }

    public void addNode(Node node) {
        sessionFactory.openSession();
        Session s = sessionFactory.getCurrentSession();
        Transaction tx = s.beginTransaction();
        s.save(node);
        tx.commit();
        s.flush();
    }

    @SuppressWarnings("unchecked")
    public Node findNodeByName(String nodeName) {
        Node node = (Node) sessionFactory.getCurrentSession().get(Node.class, nodeName);
        return node;
    }

    public void deleteNode(Node node) {
        Session s = sessionFactory.getCurrentSession();
        Transaction tx = s.beginTransaction();
        s.delete(node);
        tx.commit();
        s.flush();
    }

    public void updateNodeName(String nodeName, String newName) {
        String sql = "UPDATE node SET `name` = '"+ newName +"' WHERE `name` = '" + nodeName +"'";
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

    public void updateParrnetName(String nodeName, String parrentName) {
        String sql = "UPDATE node SET `parent` = '"+ parrentName +"' WHERE `name` = '" + nodeName +"'";
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
