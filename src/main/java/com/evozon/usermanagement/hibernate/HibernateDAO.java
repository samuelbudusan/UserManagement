package com.evozon.usermanagement.hibernate;

import com.evozon.usermanagement.dao.UserDAO;
import com.evozon.usermanagement.model.Group;
import com.evozon.usermanagement.model.Permission;
import com.evozon.usermanagement.model.UserRole;
import com.evozon.usermanagement.model.User;
import com.evozon.usermanagement.utils.UserUtil;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Primary
@Repository
public class HibernateDAO implements UserDAO {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private SessionFactory sessionFactory;

    public HibernateDAO() {
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) sessionFactory.getCurrentSession().createQuery("from User").list();
    }

    public List<Group> getAllGroups() {
        return sessionFactory.getCurrentSession().createQuery("from Group").list();
    }

    @Override
    public void updateUsers(List<User> usersList, int index) {
        Session s = sessionFactory.getCurrentSession();
        if (index != -1) {
            User user = usersList.get(index);
            s.update(user);
            s.merge(user);
        }
    }

    public void addUser(User user) {
        Session s = sessionFactory.getCurrentSession();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String sql = "INSERT INTO user(username,email, birthday, phone, firstName, lastName, password, enabled)" +
                "VALUES('" + user.getUserName() + "','" + user.getEmail() + "','" + sdf.format(user.getBirthdate()) + "','" + user.getPhone() + "','" + user.getFirstName() + "','" + user.getLastName() +
                "','" + user.getPassword() + "'," + user.getEnabled() + ")";
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

    public void addGroup(Group group) {
        Session s = sessionFactory.getCurrentSession();
        Transaction tx = s.beginTransaction();
        s.save(group);
        tx.commit();
        s.flush();
    }

    public void addRole(UserRole role) {
        Session s = sessionFactory.getCurrentSession();
        s.save(role);
        s.flush();
    }

    public void addPermissions(Set<Permission> permissions) {
        Iterator<Permission> iterator = permissions.iterator();
        while (iterator.hasNext()) {
            addPermision(iterator.next());
        }
    }

    private void addPermision(Permission permission) {
        Session s = sessionFactory.getCurrentSession();
        Transaction tx = s.beginTransaction();
        s.save(permission);
        tx.commit();
        s.flush();
    }

    public List<User> getAllSimpleUsers() {
        Iterator<User> usersIterator = sessionFactory.getCurrentSession().createQuery("from User").list().iterator();
        List<User> simpleUsersList = new ArrayList<User>();
        while (usersIterator.hasNext()) {
            User user = (User) usersIterator.next();
            if ((!UserUtil.isAdmin(user)) && (!simpleUsersList.contains(user))) {
                simpleUsersList.add(user);
            }
        }
        return simpleUsersList;
    }

    public Group findGroupByName(String groupName) {
        List<Group> groups = sessionFactory.getCurrentSession().createQuery("from Group g where g.groupName='" + groupName + "'").list();
        if(groups.size() > 0) {
            return (Group) groups.get(0);
        }
        return null;
    }

    public User findByUserName(String username) {
        List<User> users = sessionFactory.getCurrentSession().createQuery("from User u where u.userName='" + username + "'").list();
        if(users.size() > 0) {
            return (User) users.get(0);
        }
        return null;
    }

    public void addUserToGroup(String username, String groupName) {
        String sql = "INSERT INTO user_group(username,groupname)" + " VALUES('" + username + "','" + groupName + "')";
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

    public void removeUserFromGroup(String username, String groupName) {
        String sql = "DELETE FROM user_group WHERE username='" + username + "' and groupname='" + groupName + "'";
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
    public void removeRole(UserRole role) {
        Session s = sessionFactory.getCurrentSession();
        Transaction tx = s.beginTransaction();
        s.delete(role);
        tx.commit();
        s.flush();

    }

    public void removePermission(Permission permission) {
        Session s = sessionFactory.getCurrentSession();
        Transaction tx = s.beginTransaction();
        s.delete(permission);
        tx.commit();
        s.flush();
    }


}
