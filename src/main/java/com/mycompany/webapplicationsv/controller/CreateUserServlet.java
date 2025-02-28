package com.mycompany.webapplicationsv.controller;

import com.mycompany.webapplicationsv.dao.UserDAO;
import com.mycompany.webapplicationsv.model.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null || 
            (!"admin".equals(session.getAttribute("userRole")) && !"super_admin".equals(session.getAttribute("userRole")))) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }
        
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String userRole = request.getParameter("user_role");
        
        if (userName == null || userName.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and password cannot be empty.");
            request.getRequestDispatcher("/view/create.jsp").forward(request, response);
            return;
        }
        
        if (userRole == null || userRole.trim().isEmpty()) {
            userRole = "user"; // Default role is 'user'
        }
        
        UserDAO userDAO = new UserDAO();
        User newUser = new User(userName, password, userRole);
        String registrationResult = userDAO.register(newUser);
        
        if (registrationResult.equals("Registration Successful.")) {
            response.sendRedirect(request.getContextPath() + "/AdminServlet?success=1");
        } else {
            request.setAttribute("errorMessage", registrationResult);
            request.getRequestDispatcher("/view/create.jsp").forward(request, response);
        }
    }
}
