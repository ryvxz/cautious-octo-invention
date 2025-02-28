package com.mycompany.webapplicationsv.controller;

import com.mycompany.webapplicationsv.dao.HelpMessageDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/HelpServlet")
public class HelpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }
        
        String userName = (String) session.getAttribute("userName");
        String message = request.getParameter("message");
        
        if (message == null || message.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Message cannot be empty.");
            request.getRequestDispatcher("/view/help.jsp").forward(request, response);
            return;
        }
        
        HelpMessageDAO helpDAO = new HelpMessageDAO();
        
        try {
            helpDAO.createMessage(userName, message);
            request.setAttribute("successMessage", "Your message has been sent to the admin.");
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error submitting message.");
        }
        
        request.getRequestDispatcher("/view/help.jsp").forward(request, response);
    }
}
