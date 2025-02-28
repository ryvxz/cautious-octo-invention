package com.mycompany.webapplicationsv.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get the current session, do not create a new one
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            session.invalidate(); // Destroy session
        }

        // Redirect to login page with a logout success parameter
        response.sendRedirect(request.getContextPath() + "/view/login.jsp?logout=1");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response); // Handle POST requests the same way as GET
    }
}
