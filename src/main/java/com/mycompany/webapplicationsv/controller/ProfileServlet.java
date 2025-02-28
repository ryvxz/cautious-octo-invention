package com.mycompany.webapplicationsv.controller;

import com.mycompany.webapplicationsv.dao.PostDAO;
import com.mycompany.webapplicationsv.model.Post;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }
        
        String userName = (String) session.getAttribute("userName");
        PostDAO postDAO = new PostDAO();
        try {
            List<Post> posts = postDAO.getUserPosts(userName);
            request.setAttribute("posts", posts);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error retrieving posts.");
        }
        
        request.getRequestDispatcher("/view/profile.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }
        
        String userName = (String) session.getAttribute("userName");
        String action = request.getParameter("action");
        PostDAO postDAO = new PostDAO();
        
        try {
            if ("create".equals(action)) {
                String content = request.getParameter("content");
                if (content == null || content.trim().isEmpty() || content.length() > 200) {
                    request.setAttribute("errorMessage", "Post content must be between 1 and 200 characters.");
                } else {
                    postDAO.createPost(userName, content);
                }
            } else if ("delete".equals(action)) {
                int postId = Integer.parseInt(request.getParameter("postId"));
                postDAO.deletePost(postId, userName);
            }
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("errorMessage", "Error processing post request.");
        }
        
        response.sendRedirect(request.getContextPath() + "/ProfileServlet");
    }
}
