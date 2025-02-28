/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webapplicationsv.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.webapplicationsv.model.Follow;
import com.mycompany.webapplicationsv.model.User;
import com.mycompany.webapplicationsv.util.DBConnection;

public class FollowDAO
{

    public Follow getUserFollows(String userName) throws SQLException
    {
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM follows WHERE user_name = ?");
        st.setString(1, userName);
        ResultSet rs = st.executeQuery();

        if (rs.next())
        {
            Follow follow = new Follow();
            follow.setUserName(rs.getString("user_name"));
            follow.setFollow1(rs.getString("follow1"));
            follow.setFollow2(rs.getString("follow2"));
            follow.setFollow3(rs.getString("follow3"));
            return follow;
        }

        return null;
    }

    public List<User> getFollowedUsers(String userName) throws SQLException
    {
        List<User> followedUsers = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        Follow follow = getUserFollows(userName);
        if (follow == null)
        {
            return followedUsers;
        }

        UserDAO userDAO = new UserDAO();

        if (follow.getFollow1() != null)
        {
            User user = userDAO.getUserByUserName(follow.getFollow1());
            if (user != null)
            {
                followedUsers.add(user);
            }
        }

        if (follow.getFollow2() != null)
        {
            User user = userDAO.getUserByUserName(follow.getFollow2());
            if (user != null)
            {
                followedUsers.add(user);
            }
        }

        if (follow.getFollow3() != null)
        {
            User user = userDAO.getUserByUserName(follow.getFollow3());
            if (user != null)
            {
                followedUsers.add(user);
            }
        }

        return followedUsers;
    }

    public String followUser(String userName, String followUserName) throws SQLException
    {
        // Check if user exists
        UserDAO userDAO = new UserDAO();
        User followUser = userDAO.getUserByUserName(followUserName);
        if (followUser == null)
        {
            return "User does not exist.";
        }

        // Check if user is trying to follow themselves
        if (userName.equals(followUserName))
        {
            return "You cannot follow yourself.";
        }

        Connection conn = DBConnection.getInstance().getConnection();
        Follow follow = getUserFollows(userName);

        // Check if already following
        if ((follow.getFollow1() != null && follow.getFollow1().equals(followUserName))
                || (follow.getFollow2() != null && follow.getFollow2().equals(followUserName))
                || (follow.getFollow3() != null && follow.getFollow3().equals(followUserName)))
        {
            return "You are already following this user.";
        }

        // Find an empty follow slot
        PreparedStatement st;
        if (follow.getFollow1() == null)
        {
            st = conn.prepareStatement("UPDATE follows SET follow1 = ? WHERE user_name = ?");
            st.setString(1, followUserName);
            st.setString(2, userName);
        } else if (follow.getFollow2() == null)
        {
            st = conn.prepareStatement("UPDATE follows SET follow2 = ? WHERE user_name = ?");
            st.setString(1, followUserName);
            st.setString(2, userName);
        } else if (follow.getFollow3() == null)
        {
            st = conn.prepareStatement("UPDATE follows SET follow3 = ? WHERE user_name = ?");
            st.setString(1, followUserName);
            st.setString(2, userName);
        } else
        {
            return "You are already following the maximum number of users (3).";
        }

        st.executeUpdate();
        return "Successfully followed " + followUserName + ".";
    }

    public String unfollowUser(String userName, String unfollowUserName) throws SQLException
    {
        Connection conn = DBConnection.getInstance().getConnection();
        Follow follow = getUserFollows(userName);

        if (follow == null)
        {
            return "You are not following anyone.";
        }

        PreparedStatement st;
        if (unfollowUserName.equals(follow.getFollow1()))
        {
            st = conn.prepareStatement("UPDATE follows SET follow1 = NULL WHERE user_name = ?");
            st.setString(1, userName);
        } else if (unfollowUserName.equals(follow.getFollow2()))
        {
            st = conn.prepareStatement("UPDATE follows SET follow2 = NULL WHERE user_name = ?");
            st.setString(1, userName);
        } else if (unfollowUserName.equals(follow.getFollow3()))
        {
            st = conn.prepareStatement("UPDATE follows SET follow3 = NULL WHERE user_name = ?");
            st.setString(1, userName);
        } else
        {
            return "You are not following this user.";
        }

        st.executeUpdate();
        return "Successfully unfollowed " + unfollowUserName + ".";
    }
}
