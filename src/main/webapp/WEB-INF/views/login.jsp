<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:url value="/login" var="myUrl"/>

<html>

<head>
    <link rel="stylesheet" type="text/css" href="resources/style.css">

    <title>LOGIN</title>

</head>

<body>

<div class="div">

    <form name='loginForm' action="<c:url value="/j_spring_security_check" />" method='POST' cssClass="form">

        <h1 class="title">
            Login <a href="/newAccount"
                     class="newAccount">Create New Account</a>
            <a href="/resetPassword"
               class="resetPassword">Reset password</a>
        </h1>

        <input type='text' name='j_username' value=''>
        <input type='password' name='j_password'/>
        <input class="buton" name="submit" type="submit" value="submit"/>

    </form>
    <p class="wrong ">
        ${error}
    </p>
    <c:if test="${successfulAccount}">
        <p>Your account has been successfully created!</p>
    </c:if>
</div>
</body>
</html>

