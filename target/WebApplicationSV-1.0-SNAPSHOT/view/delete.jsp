<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete User</title>
</head>
<body>
    <h2>Delete User</h2>
    <form action="${pageContext.request.contextPath}/DeleteUserServlet" method="post">
        Username: <input type="text" name="username" required><br>
        <button type="submit">Delete</button>
    </form>
</body>
</html>