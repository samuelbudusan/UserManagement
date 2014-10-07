<%--
  Created by IntelliJ IDEA.
  User: samuelbudusan
  Date: 9/17/2014
  Time: 12:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:url value="/editUserByAdmin" var="myUrl" />
<html>
<head>
    <title>Edit User by Admin</title>
    <link rel="stylesheet" type="text/css" href="resources/editUserbyAdminStyle.css">
</head>
<body>

<div class="div">
    <h1 class="title">Edit information</h1>
    <form:form id="form" action="${myUrl}" commandName="simpleUser" method="POST">
        <table class="myTable">
            <tr>
                <td >Email:</td>
                <td><form:input path="email" /></td>
            </tr>
            <tr>
                <td>Birthdate:</td>
                <td><form:input action="demo_form.asp" type="date"
                                path="birthdate" /></td>
            </tr>
            <tr>
                <td>Phone:</td>
                <td><form:input path="phone" /></td>
            </tr>
            <tr>
                <td>First name:</td>
                <td><form:input path="firstName" /></td>
            </tr>
            <tr>
                <td>Last name:</td>
                <td><form:input path="lastName" /></td>
            </tr>
        </table>
        <button id="enable" class="butons" type="button">Enable</button>
        <button id="disable" class="butons" type="button">Disable</button>
        <button id="reset" class="butons" type="button">ResetPassword</button>
        <button id="cancel" class="butons" type="button">Cancel</button>
        <button class="butons" type="submit">Edit</button>
    </form:form>

    <c:if test="${fail ==0}">
        <p class="colortext">${errors[0]}</p>
        <p class="colortext">${errors[1]}</p>
        <p class="colortext">${errors[2]}</p>
        <p class="colortext">${errors[4]}</p>
    </c:if>
    </div>

<script>
    document.getElementById("cancel").onclick = function () {
        location.href = "/editUsers";
    };

    document.getElementById("enable").onclick = function () {
        location.href = "/disableAccount?enable=1";
    };

    document.getElementById("disable").onclick = function () {
        location.href = "/disableAccount?enable=0";
    };

    document.getElementById("reset").onclick = function () {
        location.href = "/resetPass";
    };
</script>
</body>
</html>
