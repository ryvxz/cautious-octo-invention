<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.mycompany.webapplicationsv.model.Post" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home - WebApplicationDB</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="navbar">
        <a href="${pageContext.request.contextPath}/view/profile.jsp">Profile</a>
        <a href="${pageContext.request.contextPath}/view/users.jsp">Users</a>
        <a href="${pageContext.request.contextPath}/view/help.jsp">Help</a>
        <a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a>
    </div>
    
    <div class="container">
        <h2>Home</h2>
        
        <% if (request.getAttribute("followedPosts") != null) { %>
            <% List<Post> followedPosts = (List<Post>) request.getAttribute("followedPosts"); %>
            <% if (followedPosts.isEmpty()) { %>
                <p>No posts to display from followed users.</p>
            <% } else { %>
                <% for (Post post : followedPosts) { %>
                    <div class="post">
                        <p><strong><%= post.getUserName() %></strong></p>
                        <p><%= post.getPostContent() %></p>
                        <p class="timestamp"><%= post.getPostDate() %></p>
                    </div>
                <% } %>
            <% } %>
        <% } else { %>
            <p>No posts available.</p>
        <% } %>
    </div>
</body>
</html>
