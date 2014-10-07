<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/resetPassword" var="myUrl"/>

<html>

<head>
    <link rel="stylesheet" type="text/css"
          href="resources/resetPasswordStyle.css">
    <title>ResetPassword</title>

</head>

<body>

<div class="div">
    <h1 class="title">Reset password</h1>
    <form:form action="${myUrl}" commandName="user" method="POST">
        <table class="myTable">
            <tr>
                <td>Email:</td>
                <td><form:input path="email"/></td>
            </tr>
        </table>
        <a href="/login">
            <button
                    class="butons" type="button">Cancel
            </button>
        </a>
        <button class="butons" type="submit">Reset password</button>
        <c:if test="${fail ==0}">
            <p class="wrong">${errors}<br>Please try again.</p>
        </c:if>
    </form:form>
</div>
</body>
</html>