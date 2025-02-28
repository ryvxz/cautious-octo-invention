<!-- result.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
</head>
<body>
    <h2>Operation Result</h2>
    <p>${message}</p>
    <a href="${pageContext.request.contextPath}/view/admin.jsp">Back to Admin Panel</a>
</body>
</html>
