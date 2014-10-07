<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="resources/editGroup.css">
    <title>Edit Group</title>
</head>
<body>

<div class="div">
    <h1 class="title">${group.groupName}</h1>
    <div id="table_wrapper1">
        <table class="table1">
            <tr>
                <td class="t1-1">Users</td>
            </tr>
        </table>
        <div id="tbody1">
            <form:form class="table2" action="/addUser" commandName="addedUsers" method="POST" id="myform">
                <table>
                    <form:input type="hidden" path="groupName" value="${group.groupName}"/>
                    <c:forEach items="${allUsers}" var="user">
                        <tr>
                            <td class="td1-1"><form:checkbox path="users" value="${user.userName}" label="${user.userName}" /></td>
                        </tr>
                    </c:forEach>
                </table>

            </form:form>
        </div>
    </div>

    <div id="table_wrapper2">
        <table class="table1">
            <tr>
                <td class="t1-2">Existing members</td>
                <td class="t2-2">Role</td>
            </tr>
        </table>
        <div id="tbody2">
            <table class="table2" id="groupTable">
                    <c:forEach items="${groupMembers}" var="user">
                        <tr onclick="myFunction(this,'${group.groupName}')">
                            <td class="td1-2">${user.userName}</td>
                            <td class="td2-2">
                                <c:set var="userROLE" value="SIMPLE USER" />
                                <c:forEach items="${user.userRole}" var="role">
                                    <c:if test="${role.role == 'ROLE_MODERATOR'}">
                                        <c:if test="${role.groupName == group.groupName}">
                                            <c:set var="userROLE" value="MODERATOR" />
                                        </c:if>
                                    </c:if>
                                 </c:forEach>
                                <c:out value="${userROLE}"></c:out>
                            </td>
                        </tr>
                    </c:forEach>
            </table>
        </div>
    </div>


    <a href="/groupsManagement">
        <button class="butons" type="button">Back</button>
    </a>
    <button class="butons" type="submit"  form="myform">Add</button>
</div>

<script>
    function myFunction(x,groupName) {
        var str = "/changeRole?index="+x.rowIndex+"&groupname="+groupName;
        window.location = str;
    }
</script>

</body>
</html>
