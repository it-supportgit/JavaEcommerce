package com.example.dao;
import com.example.model.User;
import com.example.util.DatabaseUtil;
import java.sql.*; import java.util.*;
public class UserDao {
    public User findById(String id) throws Exception {
        String query = "SELECT id, username, password FROM users WHERE id = " + id;
        try (Connection c = DatabaseUtil.getConnection(); Statement s = c.createStatement(); ResultSet rs = s.executeQuery(query)) {
            if (rs.next()) return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
        }
        return null;
    }
}
