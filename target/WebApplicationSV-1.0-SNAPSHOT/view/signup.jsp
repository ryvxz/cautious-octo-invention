<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Sign Up - WebApplicationDB</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    </head>
    <body>
        <div class="container">
            <h2>Sign Up</h2>

            <% if (request.getAttribute("errorMessage") != null)
            {%>
            <p style="color: red;"><%= request.getAttribute("errorMessage")%></p>
            <% }%>

            <form action="${pageContext.request.contextPath}/SignupServlet" method="post">
                <input type="text" name="username" placeholder="Username" required>
                <input type="password" name="password" placeholder="Password" required>
                <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
                <button type="submit">Sign Up</button>
            </form>

            <p>Already have an account? <a href="${pageContext.request.contextPath}/view/login.jsp">Login here</a></p>
        </div>
    </body>
</html>
