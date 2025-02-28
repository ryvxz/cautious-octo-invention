package com.mycompany.webapplicationsv.controller;

import com.mycompany.webapplicationsv.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
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
        String newPassword = request.getParameter("password");
        String newRole = request.getParameter("user_role");
        
        if (userName == null || userName.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Username cannot be empty.");
            request.getRequestDispatcher("/view/update.jsp").forward(request, response);
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        String updateResult = userDAO.updateUser(userName, newPassword, newRole);
        
        if (updateResult.equals("User updated successfully.")) {
            response.sendRedirect(request.getContextPath() + "/AdminServlet?success=1");
        } else {
            request.setAttribute("errorMessage", updateResult);
            request.getRequestDispatcher("/view/update.jsp").forward(request, response);
        }
    }
}
