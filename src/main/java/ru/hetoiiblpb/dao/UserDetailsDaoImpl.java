package ru.hetoiiblpb.dao;

import ru.hetoiiblpb.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.SQLException;

@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDAOHibernateImpl userDAOHibernateImpl;

    @Override
    public User findUserByUsername(String username) throws SQLException {
        return sessionFactory.getCurrentSession().get(User.class, userDAOHibernateImpl.getUserByName(username).getId());
    }
}
