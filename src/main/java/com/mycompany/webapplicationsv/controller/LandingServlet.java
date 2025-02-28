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

@WebServlet("/LandingServlet")
public class LandingServlet extends HttpServlet {
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
            List<Post> followedPosts = postDAO.getFollowedUsersPosts(userName);
            request.setAttribute("followedPosts", followedPosts);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error retrieving posts.");
        }
        
        request.getRequestDispatcher("/view/landing.jsp").forward(request, response);
    }
}
