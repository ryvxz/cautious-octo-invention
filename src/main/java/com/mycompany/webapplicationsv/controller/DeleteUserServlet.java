package com.mycompany.webapplicationsv.controller;

import com.mycompany.webapplicationsv.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
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
        
        String[] selectedUsers = request.getParameterValues("selectedUsers");
        if (selectedUsers == null || selectedUsers.length == 0) {
            request.setAttribute("errorMessage", "No users selected for deletion.");
            request.getRequestDispatcher("/view/delete.jsp").forward(request, response);
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        
        for (String userName : selectedUsers) {
            userDAO.deleteUser(userName);
        }
        
        response.sendRedirect(request.getContextPath() + "/AdminServlet?success=1");
    }
}
