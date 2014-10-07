<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:url value="/newAccount" var="myUrl"/>
<html>

<head>
    <link rel="stylesheet" type="text/css"
          href="resources/newAccountStyle.css">
    <title>NewAccount</title>

</head>

<body>

<div class="div">
    <h1 class="title">Create New Account</h1>
    <form:form action="${myUrl}" commandName="user" method="POST">
        <table class="myTable">
            <tr>
                <td>Username:</td>
                <td><form:input path="userName"/></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><form:input path="email"/></td>
            </tr>
            <tr>
                <td>Birthdate:</td>
                <td><form:input action="demo_form.asp" type="date"
                                path="birthdate"/></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><form:input path="phone"/></td>
            </tr>
            <tr>
                <td>First name:</td>
                <td><form:input path="firstName"/></td>
            </tr>
            <tr>
                <td>Last name:</td>
                <td><form:input path="lastName"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><form:password path="password"/></td>
            </tr>
        </table>
        <a href="/login">
            <button
                    class="butons" type="button">Cancel
            </button>
        </a>
        <button class="butons" type="submit">Create account</button>
    </form:form>

    <c:if test="${fail ==0}">
        <p>${errors[0]}</p>

        <p>${errors[1]}</p>

        <p>${errors[2]}</p>

        <p>${errors[4]}</p>
    </c:if>
</div>
</body>
</html>

