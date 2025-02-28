<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update User</title>
</head>
<body>
    <h2>Update User Information</h2>
    <form action="${pageContext.request.contextPath}/UpdateUserServlet" method="post">
        Username: <input type="text" name="username" required><br>
        New Password: <input type="password" name="password"><br>
        New Role:
        <select name="role">
            <option value="user">User</option>
            <option value="admin">Admin</option>
        </select><br>
        <button type="submit">Update</button>
    </form>
</body>
</html>