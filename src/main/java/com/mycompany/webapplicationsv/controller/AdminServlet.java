package com.mycompany.webapplicationsv.controller;

import com.mycompany.webapplicationsv.dao.UserDAO;
import com.mycompany.webapplicationsv.dao.HelpMessageDAO;
import com.mycompany.webapplicationsv.model.User;
import com.mycompany.webapplicationsv.model.HelpMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null || 
            (!"admin".equals(session.getAttribute("userRole")) && !"super_admin".equals(session.getAttribute("userRole")))) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        HelpMessageDAO helpDAO = new HelpMessageDAO();
        
        try {
            List<User> users = userDAO.getAllUsers();
            List<HelpMessage> messages = helpDAO.getRecentMessages(5);
            
            request.setAttribute("users", users);
            request.setAttribute("messages", messages);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error retrieving admin data.");
        }
        
        request.getRequestDispatcher("/view/admin.jsp").forward(request, response);
    }
}
