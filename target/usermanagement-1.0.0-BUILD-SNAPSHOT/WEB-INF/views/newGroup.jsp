<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:url value="/newGroup" var="myUrl" />
<html>
<head>
    <link rel="stylesheet" type="text/css"
          href="resources/newAccountStyle.css">
    <title>New Group</title>
</head>
<body>

<div class="div" id="newGroup" style="height: 200px">
    <h1 class="title">New Group</h1>
    <form:form action="${myUrl}" commandName="group" method="POST">
        <table class="myTable">
            <tr>
                <td>Group name:</td>
                <td><form:input path="groupName" /></td>
            </tr>
        </table>
        <a href="/groupsManagement"><button
                class="butons" type="button">Cancel</button></a>
        <button class="butons" type="submit">Create group</button>
    </form:form>

    <c:if test="${fail == 0}">
        <p>${errors}</p>
    </c:if>
</div>

</body>
</html>
