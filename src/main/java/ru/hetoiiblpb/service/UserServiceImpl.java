package ru.hetoiiblpb.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hetoiiblpb.dao.UserDAO;
import ru.hetoiiblpb.exception.DBException;
import ru.hetoiiblpb.model.User;

import java.sql.SQLException;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private static UserServiceImpl instance;
    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }


    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    @Transactional
    public List<User> getAllUsers() throws DBException {
        try {
            return userDAO.getAllUsers();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    @Transactional
    public boolean addUser(User user) throws DBException {
        if (user.getEmail().isEmpty() || user.getName().isEmpty()) {
            return false;
        }
        try {
            if (userDAO.checkUserByEmail(user.getEmail())) {
                return userDAO.addUser(user);
            }
        } catch (SQLException e) {
            throw new DBException(e);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) throws DBException {
        try {
            return userDAO.deleteUser(id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    @Transactional
    public boolean updateUser(User user) throws DBException {
        if (user.getEmail().isEmpty() || user.getName().isEmpty()) {
            return false;
        }
        try {
            return userDAO.updateUser(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    @Transactional
    public User getUserById(Long id) throws DBException {
        try {
            return userDAO.getUserById(id);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    @Transactional
    public boolean checkUserByEmail(String mail) throws DBException {
        try {
            return userDAO.checkUserByEmail(mail);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    @Override
    @Transactional
    public User verifyUserPassword(String name, String password) throws DBException {
        if (name.isEmpty() || password.isEmpty()) {
            return null;
        }
        try {
            return userDAO.verifyUserPassword(name, password);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}
