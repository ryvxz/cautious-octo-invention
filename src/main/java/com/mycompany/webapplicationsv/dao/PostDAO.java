/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webapplicationsv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import com.mycompany.webapplicationsv.model.Post;
import com.mycompany.webapplicationsv.util.DBConnection;

public class PostDAO
{

    public void createPost(String userName, String content) throws SQLException
    {
        Connection conn = DBConnection.getInstance().getConnection();

        // Count existing posts
        PreparedStatement countSt = conn.prepareStatement(
                "SELECT COUNT(*) AS post_count FROM posts WHERE user_name = ?");
        countSt.setString(1, userName);
        ResultSet countRs = countSt.executeQuery();
        int postCount = 0;
        if (countRs.next())
        {
            postCount = countRs.getInt("post_count");
        }

        conn.setAutoCommit(false);
        try
        {
            if (postCount >= 5)
            {
                // Delete oldest post
                PreparedStatement deleteSt = conn.prepareStatement(
                        "DELETE FROM posts WHERE user_name = ? ORDER BY post_date ASC LIMIT 1");
                deleteSt.setString(1, userName);
                deleteSt.executeUpdate();

                // Reorder the remaining posts
                PreparedStatement reorderSt = conn.prepareStatement(
                        "SET @order = 0; UPDATE posts SET post_order = (@order:=@order+1) "
                        + "WHERE user_name = ? ORDER BY post_date ASC");
                reorderSt.setString(1, userName);
                reorderSt.executeUpdate();
            }

            // Insert new post
            PreparedStatement insertSt = conn.prepareStatement(
                    "INSERT INTO posts(user_name, post_content, post_order) VALUES (?, ?, ?)");
            insertSt.setString(1, userName);
            insertSt.setString(2, content);
            insertSt.setInt(3, Math.min(postCount + 1, 5)); // Ensure we don't exceed 5
            insertSt.executeUpdate();

            conn.commit();
        } catch (SQLException e)
        {
            conn.rollback();
            throw e;
        } finally
        {
            conn.setAutoCommit(true);
        }
    }

    public List<Post> getUserPosts(String userName) throws SQLException
    {
        List<Post> posts = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();
        PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM posts WHERE user_name = ? ORDER BY post_date DESC");
        st.setString(1, userName);
        ResultSet rs = st.executeQuery();

        while (rs.next())
        {
            Post post = new Post();
            post.setPostId(rs.getInt("post_id"));
            post.setUserName(rs.getString("user_name"));
            post.setPostContent(rs.getString("post_content"));
            post.setPostDate(rs.getTimestamp("post_date"));
            post.setPostOrder(rs.getInt("post_order"));
            posts.add(post);
        }

        return posts;
    }

    public List<Post> getFollowedUsersPosts(String userName) throws SQLException
    {
        List<Post> posts = new ArrayList<>();
        Connection conn = DBConnection.getInstance().getConnection();

        PreparedStatement st = conn.prepareStatement(
                "SELECT p.* FROM posts p "
                + "JOIN follows f ON p.user_name = f.follow1 OR p.user_name = f.follow2 OR p.user_name = f.follow3 "
                + "WHERE f.user_name = ? "
                + "ORDER BY p.post_date DESC");
        st.setString(1, userName);
        ResultSet rs = st.executeQuery();

        while (rs.next())
        {
            Post post = new Post();
            post.setPostId(rs.getInt("post_id"));
            post.setUserName(rs.getString("user_name"));
            post.setPostContent(rs.getString("post_content"));
            post.setPostDate(rs.getTimestamp("post_date"));
            post.setPostOrder(rs.getInt("post_order"));
            posts.add(post);
        }

        return posts;
    }

    public void deletePost(int postId, String userName) throws SQLException
    {
        Connection conn = DBConnection.getInstance().getConnection();
        conn.setAutoCommit(false);

        try
        {
            // Check if post belongs to user
            PreparedStatement checkSt = conn.prepareStatement(
                    "SELECT * FROM posts WHERE post_id = ? AND user_name = ?");
            checkSt.setInt(1, postId);
            checkSt.setString(2, userName);
            ResultSet rs = checkSt.executeQuery();

            if (!rs.next())
            {
                throw new SQLException("Post not found or you don't have permission to delete it.");
            }

            // Delete the post
            PreparedStatement deleteSt = conn.prepareStatement(
                    "DELETE FROM posts WHERE post_id = ?");
            deleteSt.setInt(1, postId);
            deleteSt.executeUpdate();

            // Reorder the remaining posts
            PreparedStatement reorderSt = conn.prepareStatement(
                    "SET @order = 0; UPDATE posts SET post_order = (@order:=@order+1) "
                    + "WHERE user_name = ? ORDER BY post_date ASC");
            reorderSt.setString(1, userName);
            reorderSt.executeUpdate();

            conn.commit();
        } catch (SQLException e)
        {
            conn.rollback();
            throw e;
        } finally
        {
            conn.setAutoCommit(true);
        }
    }
}
