<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Jenkins jobs are here ! ! !</title>
</head>
<body>
		<body>
    <h1>List of Jenkins Jobs</h1>
    <ul>
        <c:forEach items="${msg}" var="jobName">
            <li>${jobName}</li>
        </c:forEach>
    </ul>
</body>
</body>
</html>