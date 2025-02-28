package com.mycompany.webapplicationsv.controller;

import com.mycompany.webapplicationsv.dao.FollowDAO;
import com.mycompany.webapplicationsv.dao.UserDAO;
import com.mycompany.webapplicationsv.model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UsersServlet")
public class UsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            response.sendRedirect("request.getContextPath() + /view/login.jsp");
            return;
        }
        
        String userName = (String) session.getAttribute("userName");
        FollowDAO followDAO = new FollowDAO();
        UserDAO userDAO = new UserDAO();
        
        try {
            List<User> followedUsers = followDAO.getFollowedUsers(userName);
            List<User> allUsers = userDAO.getAllUsers();
            
            request.setAttribute("followedUsers", followedUsers);
            request.setAttribute("allUsers", allUsers);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error retrieving users.");
        }
        
        request.getRequestDispatcher("/view/users.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            response.sendRedirect("request.getContextPath() + /view/login.jsp");
            return;
        }
        
        String userName = (String) session.getAttribute("userName");
        String action = request.getParameter("action");
        String targetUser = request.getParameter("targetUser");
        FollowDAO followDAO = new FollowDAO();
        
        try {
            String resultMessage = "";
            if ("follow".equals(action)) {
                resultMessage = followDAO.followUser(userName, targetUser);
            } else if ("unfollow".equals(action)) {
                resultMessage = followDAO.unfollowUser(userName, targetUser);
            }
            request.setAttribute("message", resultMessage);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error processing follow/unfollow request.");
        }
        
        response.sendRedirect(request.getContextPath() + "/UsersServlet");
    }
}