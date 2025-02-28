<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Help - WebApplicationDB</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="navbar">
        <a href="${pageContext.request.contextPath}/LandingServlet">Home</a>
        <a href="${pageContext.request.contextPath}/view/profile.jsp">Profile</a>
        <a href="${pageContext.request.contextPath}/view/users.jsp">Users</a>
        <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
    </div>
    
    <div class="container">
        <h2>Help & Support</h2>
        <p>If you have any issues or questions, you can send a message to the admin.</p>
        
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
        <% } %>
        
        <% if (request.getAttribute("successMessage") != null) { %>
            <p style="color: green;"><%= request.getAttribute("successMessage") %></p>
        <% } %>
        
        <form action="${pageContext.request.contextPath}/HelpServlet" method="post">
            <textarea name="message" placeholder="Write your message here..." required></textarea>
            <button type="submit">Send Message</button>
        </form>
    </div>
</body>
</html>
