<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Yahooo</title>
</head>

<body>
<p><font color="red">${errorMessage}</font></p>
<form action="/spring-mvc/login" method="POST">
Enter your name
<input type="text" name="name"/>
Enter your password
<input type="password" name="password"/>
<input type="submit"/>
</form>
</body>
</html>