package ru.hetoiiblpb.dao;

import ru.hetoiiblpb.model.User;

import java.sql.SQLException;

public interface UserDetailsDao {
    User findUserByUsername(String username) throws SQLException;
}
