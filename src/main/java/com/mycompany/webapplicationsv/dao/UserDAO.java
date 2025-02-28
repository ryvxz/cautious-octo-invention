package com.mycompany.webapplicationsv.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.webapplicationsv.model.User;
import com.mycompany.webapplicationsv.util.DBConnection;

public class UserDAO
{

    public boolean login(String userName, String password)
    {
        boolean result = false;
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM account WHERE user_name = ? AND password = ?");
            st.setString(1, userName);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next())
            {
                result = true;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public String register(User user)
    {
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();

            // Check if username already exists
            PreparedStatement checkSt = conn.prepareStatement("SELECT * FROM account WHERE user_name = ?");
            checkSt.setString(1, user.getUserName());
            ResultSet rs = checkSt.executeQuery();
            if (rs.next())
            {
                return "Username already exists.";
            }

            // Insert user
            PreparedStatement st = conn.prepareStatement("INSERT INTO account(user_name, password, user_role) VALUES (?, ?, ?)");
            st.setString(1, user.getUserName());
            st.setString(2, user.getPassword());
            st.setString(3, user.getUserRole() != null ? user.getUserRole() : "user");
            st.execute();

            // Create empty follows record
            PreparedStatement follows = conn.prepareStatement("INSERT INTO follows(user_name) VALUES (?)");
            follows.setString(1, user.getUserName());
            follows.execute();

            return "Registration Successful.";
        } catch (SQLException e)
        {
            e.printStackTrace();
            return "Registration Failed: " + e.getMessage();
        }
    }

    public User getUserByUserName(String userName)
    {
        User user = null;
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM account WHERE user_name = ?");
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();

            if (rs.next())
            {
                user = new User();
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(rs.getString("user_role"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM account WHERE user_role = 'user'");
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                User user = new User();
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(rs.getString("user_role"));
                users.add(user);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getAllAdmins()
    {
        List<User> admins = new ArrayList<>();
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM account WHERE user_role = 'admin'");
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                User user = new User();
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(rs.getString("user_role"));
                admins.add(user);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return admins;
    }

    public List<User> getAllAccounts()
    {
        List<User> accounts = new ArrayList<>();
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM account");
            ResultSet rs = st.executeQuery();

            while (rs.next())
            {
                User user = new User();
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setUserRole(rs.getString("user_role"));
                accounts.add(user);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return accounts;
    }

    public String updateUser(String userName, String newPassword, String newRole)
    {
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            StringBuilder sql = new StringBuilder("UPDATE account SET ");
            ArrayList<String> params = new ArrayList<>();
            ArrayList<String> values = new ArrayList<>();

            if (newPassword != null && !newPassword.trim().isEmpty())
            {
                params.add("password = ?");
                values.add(newPassword);
            }

            if (newRole != null && !newRole.trim().isEmpty())
            {
                params.add("user_role = ?");
                values.add(newRole);
            }

            if (params.isEmpty())
            {
                return "No changes specified.";
            }

            sql.append(String.join(", ", params));
            sql.append(" WHERE user_name = ?");

            PreparedStatement st = conn.prepareStatement(sql.toString());
            for (int i = 0; i < values.size(); i++)
            {
                st.setString(i + 1, values.get(i));
            }
            st.setString(values.size() + 1, userName);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0)
            {
                return "User updated successfully.";
            } else
            {
                return "User not found.";
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return "Update Failed: " + e.getMessage();
        }
    }

    public String deleteUser(String userName)
    {
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement("DELETE FROM account WHERE user_name = ?");
            st.setString(1, userName);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0)
            {
                return "User deleted successfully.";
            } else
            {
                return "User not found.";
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return "Deletion Failed: " + e.getMessage();
        }
    }

    public String getUserRole(String userName)
    {
        try
        {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT user_role FROM account WHERE user_name = ?");
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();

            if (rs.next())
            {
                return rs.getString("user_role");
            } else
            {
                return null;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
