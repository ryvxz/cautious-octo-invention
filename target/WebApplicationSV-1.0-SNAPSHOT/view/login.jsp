<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - WebApplicationDB</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Login</h2>
        
        <% if (request.getParameter("success") != null) { %>
            <p style="color: green;">Account created successfully. Please log in.</p>
        <% } %>
        
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
        <% } %>
        
        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
            <input type="text" name="username" placeholder="Username" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
        
       <p>Don't have an account? <a href="${pageContext.request.contextPath}/view/signup.jsp">Sign up here</a></p>
    </div>
</body>
</html>
