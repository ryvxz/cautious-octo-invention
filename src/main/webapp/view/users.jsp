<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.mycompany.webapplicationsv.model.User" %>
<!DOCTYPE html>
<html>
<head>
    <title>Users - WebApplicationDB</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="navbar">
        <a href="${pageContext.request.contextPath}/LandingServlet">Home</a>
        <a href="${pageContext.request.contextPath}/view/profile.jsp">Profile</a>
        <a href="${pageContext.request.contextPath}/view/help.jsp">Help</a>
        <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
    </div>
    
    <div class="container">
        <h2>Follow Users</h2>
        
        <h3>Follow a User</h3>
        <form action="${pageContext.request.contextPath}/UsersServlet" method="post">
            <input type="text" name="targetUser" placeholder="Enter username" required>
            <input type="hidden" name="action" value="follow">
            <button type="submit">Follow</button>
        </form>
        
        <h3>Followed Users</h3>
        <% if (request.getAttribute("followedUsers") != null) { %>
            <% List<User> followedUsers = (List<User>) request.getAttribute("followedUsers"); %>
            <% if (followedUsers.isEmpty()) { %>
                <p>You are not following anyone.</p>
            <% } else { %>
                <% for (User user : followedUsers) { %>
                    <div class="user">
                        <p><%= user.getUserName() %></p>
                        <form action="${pageContext.request.contextPath}/UsersServlet" method="post">
                            <input type="hidden" name="targetUser" value="<%= user.getUserName() %>">
                            <input type="hidden" name="action" value="unfollow">
                            <button type="submit">Unfollow</button>
                        </form>
                    </div>
                <% } %>
            <% } %>
        <% } else { %>
            <p>No followed users available.</p>
        <% } %>
    </div>
</body>
</html>
