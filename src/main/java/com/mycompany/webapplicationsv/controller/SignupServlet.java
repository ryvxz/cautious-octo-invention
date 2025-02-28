/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (userName == null || userName.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username and password cannot be empty");
            request.getRequestDispatcher("/view/signup.jsp").forward(request, response);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match");
            request.getRequestDispatcher("/view/signup.jsp").forward(request, response);
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        User newUser = new User(userName, password, "user"); // Default role is 'user'
        String registrationResult = userDAO.register(newUser);
        
        if (registrationResult.equals("Registration Successful.")) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp?success=1");
        } else {
            request.setAttribute("errorMessage", registrationResult);
            request.getRequestDispatcher("/view/signup.jsp").forward(request, response);


        }
    }
}